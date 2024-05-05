import chisel3._
import chisel3.util._

/**
  * This is the top level to for the UART output and a test blinking LED.
  */
class BitBang(frequ: Int) extends Module {
  val io = IO(new Bundle {
    val rx = Input(Bool())
    val tx = Output(Bool())
    val sw = Input(UInt(4.W))
    val led = Output(UInt(8.W))
  })
  io.tx := io.rx
  io.led := 55.U ## io.sw
}

// generate Verilog
object BitBang extends App {
  emitVerilog(new BitBang(100000000),  Array("--target-dir", "generated"))
}