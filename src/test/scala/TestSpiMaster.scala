/*
 * Dummy tester to start a Chisel project.
 *
 * Author: Martin Schoeberl (martin@jopdesign.com)
 * 
 */

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TestSpiMaster extends AnyFlatSpec with ChiselScalatestTester {

  "SpiMaster" should "work" in {
    test(new SpiMaster) { dut =>
    }
  }
}
