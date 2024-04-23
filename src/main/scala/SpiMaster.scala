import chisel3._
import chisel3.util._
class SpiMaster extends Module {
  val spi = IO (new Bundle {
    val ncs = Output(Bool())
    val sclk = Output(Bool())
    val mosi = Output(Bool())
    val miso = Input(Bool())
  })
  val io = IO(new Bundle {
    val dataOut = Output(UInt(8.W))
    val dataIn = Input(UInt(8.W))
    val dataValid = Input(Bool())
  })

  object State extends ChiselEnum {
    val idle, tx, rx = Value
  }
  import State._
  val state = RegInit(idle)

  spi.ncs := 0.U
  spi.sclk := 0.U
  spi.mosi := 0.U

  io.dataOut := 0.U

}

object SpiMaster extends App {
  emitVerilog(new SpiMaster(), Array("--target-dir", "generated"))
}
