    ; ---- > 1: if (x) x = 0; 
    rvalue x
    gofalse 17
    lvalue x
    push 0
    store 
    ; ---- > 2: if (y) {x = 0 ; y = y;}
label 17
    ; ---- 
    rvalue y
    gofalse 18
    lvalue x
    push 0
    store 
    ; ---- 
    lvalue y
    rvalue y
    store 
    ; ---- > 3: print("(4+1)*2 = " + (4+1)*2);
label 18
    ; ---- 
    print "(4+1)*2 = "
    push 4
    inc 
    copy 
    + 
    write 
    ; ---- > 4: x = 15%8;
    lvalue x
        push 15
        push 8
        mod 
    store 
    ; ---- > 5: print(x*x/2);
    rvalue x
    rvalue x
    * 
    push 2
    div 
    write 
    ; ---- > 6: if (x) x = x-1;
    rvalue x
    gofalse 19
    lvalue x
        rvalue x
        dec 
    store 
    ; ---- > 7: x = y = 0;
label 19
    ; ---- 
    lvalue x
        lvalue y
        push 0
        store 
    rvalue y
    store 
