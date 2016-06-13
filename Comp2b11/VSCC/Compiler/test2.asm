    ; ---- > 1: 
    ; ---- > 2: {
    ; ---- > 3: 
    ; ---- > 4: print("\nTest 2.1 ");
    print "\nTest 2.1 "
    ; ---- > 5: if (3 < 6 - 1) print("okay"); else print("fails");
    push 3
    push 6
    dec 
    < 
    gofalse 17
    print "okay"
    goto 18
label 17
    print "fails"
    ; ---- > 6: 
    ; ---- > 7: print("\nTest 2.2 ");
label 18
    ; ---- 
    print "\nTest 2.2 "
    ; ---- > 8: if (1 + 2 < 3) print("fails"); else print("okay");
    push 1
    push 2
    + 
    push 3
    < 
    gofalse 19
    print "fails"
    goto 20
label 19
    print "okay"
    ; ---- > 9: 
    ; ---- > 10: for (i = 0; i < 0; i++) print("\nfor-loop fails");
label 20
    ; ---- 
    lvalue i
    push 0
    store 
label 21
    rvalue i
    push 0
    < 
    gotrue 23
    goto 22
label 24
    rvalue i
    lvalue i
    rvalue i
    inc 
    store 
    pop 
    goto 21
label 23
    print "\nfor-loop fails"
    ; ---- > 11: 
    ; ---- > 12: if (1) print(""); else print("\nif-else fails");
    goto 24
label 22
    ; ---- 
    push 1
    gofalse 25
    print ""
    goto 26
label 25
    print "\nif-else fails"
    ; ---- > 13: 
    ; ---- > 14: print("\nTest 2.3 ");
label 26
    ; ---- 
    print "\nTest 2.3 "
    ; ---- > 15: if (this <is <wrong) print("this statement should not have compiled");
    rvalue this
    rvalue is
    < 
    ; ---- > 17: --x;
label 27
    ; ---- > 19: }
    ; ---- > 20: 

Error in your source program at line 15
if (this <is <wrong) print("this statement should not have compiled");
             ^
Unexpected input RELOP[<]. RPAREND expected.  Skipping ...
Resuming parsing at marked token in line 17
--x;
^^
Error in your source program at line 17
--x;
^^
No statement begins with INCOP[--].  Skipping ...
Resuming parsing at marked token in line 19
}
^
