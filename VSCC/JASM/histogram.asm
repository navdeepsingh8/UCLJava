print "Enter histogram values on one line, last value 0>"
lvalue n
read
store
label loop:
	rvalue n
	push 0
	>
	gofalse next
	print "\n"

label loop2:
	rvalue n
	push 0
	>
	gofalse next2
	
	print "#"
	lvalue n
	rvalue n
	dec
	store
	goto loop2

label next2:
	lvalue n
	read
	store
	goto loop

label next:
	print "\n"
