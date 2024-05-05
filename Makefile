# Generate Verilog code
doit:
	sbt run

# Run the test
test:
	sbt test

bit:
	scp masca@chipdesign1.compute.dtu.dk:~/vivado/spi/spi.runs/impl_1/BitBang.bit .

# Configure the Basys3 or NexysA7 board with open source tools

config:
	openocd -f 7series.txt

screen:
	screen /dev/cu.usbserial-210292B408601 115200

clean:
	git clean -fd

