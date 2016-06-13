lvalue n
push 0
store

loop:
	print "\nEnter size of box > 0"
	lvalue n
	read
	store
	print "\n"
	
	lvalue i
	push 0
	store
	
	loop2:
		rvalue i
		rvalue n
		<
		gofalse next2

		lvalue m
		rvalue n
		store
		
		loop3:
			rvalue m
			push 0
			>
			gofalse next3
			
			rvalue i
			rvalue m
			+
			push 2
			%
			0
			=
			gofalse not4
			print "#"
			goto next4
		not4:
			print "."
		next4:	
			lvalue m
			rvalue m
			dec
			store
			goto loop3
		next3:
			print "\n"
			lvalue i
			rvalue i
			inc
			store
			goto loop2
	next2:
		rvalue n
		push 0
		>
		gotrue loop
			
			
	
	