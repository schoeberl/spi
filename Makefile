# Generate Verilog code
doit:
	sbt run

# Run the test
test:
	sbt test

# Configure the Basys3  or NexysA7 board with open source tools

config:
	openocd -f 7series.txt

clean:
	git clean -fd

