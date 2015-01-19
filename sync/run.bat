@ECHO OFF
SET NODE_CONFIG_DIR=./www/config/
:loop
	node index
	goto loop