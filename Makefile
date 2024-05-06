# Generate Verilog code
doit:
	sbt run

# Run the test
test:
	sbt test

# synthesize with a small script
# ./vivado_synth -t basys3_top -p xc7a35tcpg236-1 -x basys.xdc -o build src/basys3_top.v src/ChiselTop.v src/tt_um_chisel_pong.v

synpath:
	source /home/shared/Xilinx/Vivado/2017.4/settings64.sh

synth:
	./vivado_synth -t BitBang -p xc7a100tcsg324-1 -x nexysA7.xdc -o build generated/BitBang.v

bit:
	scp masca@chipdesign1.compute.dtu.dk:~/source/spi/build/BitBang.bit .

# Configure the Basys3 or NexysA7 board with open source tools

config:
	openocd -f 7series.txt

screen:
	screen /dev/cu.usbserial-210292B408601 115200

clean:
	git clean -fd

