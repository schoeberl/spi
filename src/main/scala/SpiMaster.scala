import chisel3._
import chisel3.util._
class SpiMaster extends Module {
  val io = IO(new Bundle {
    val sclk = Output(Bool())
    val mosi = Output(Bool())
    val cs = Output(Bool())
    val dataOut = Output(UInt(8.W))
    val dataIn = Input(UInt(8.W))
    val dataValid = Input(Bool())
  })

  object State extends ChiselEnum {
    val idle, tx, rx = Value
  }
  import State._
  val state = RegInit(idle)

  val cnt = RegInit(0.U(4.W))
  val dataReg = RegInit(0.U(8.W))
  val dataInReg = RegInit(0.U(8.W))

  val sclkReg = RegInit(false.B)
  val mosiReg = RegInit(false.B)
  val csReg = RegInit(true.B)

  io.sclk := sclkReg
  io.mosi := mosiReg
  io.cs := csReg
  io.dataOut := dataReg

  when(state === idle) {
    cnt := 0.U
    dataReg := 0.U
    dataInReg := 0.U
    sclkReg := false.B
    mosiReg := false.B
    csReg := true.B
    when(io.dataValid) {
      state := tx
    }
  }

  when(state === tx) {
    cnt := cnt + 1.U
    when(cnt === 0.U) {
      csReg := false.B
    }
    when(cnt === 1.U) {
      sclkReg := true.B
    }
    when(cnt === 9.U) {
      sclkReg := false.B
    }
    when(cnt >= 1.U && cnt <= 8.U) {
      mosiReg := dataReg(7.U - cnt)
    }
    when(cnt === 9.U) {
      state := rx
    }
  }

  when(state === rx) {
    cnt := cnt + 1.U
    when(cnt === 0.U) {
      csReg := false.B
    }
    when(cnt === 1.U) {
      sclkReg := true.B
    }
    when(cnt >= 1.U && cnt <= 8.U) {
      dataInReg := Cat(dataInReg(6, 0), io.dataIn)
    }
    when(cnt === 9.U) {
      sclkReg := false.B
      dataReg :=
        Mux(dataInReg(7.U) === true.B, dataInReg, dataInReg | 0x80.U)
    }
  }
}

object SpiMaster extends App {
  emitVerilog(new SpiMaster(), Array("--target-dir", "generated"))
}
