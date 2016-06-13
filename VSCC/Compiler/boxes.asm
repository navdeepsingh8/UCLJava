    ; ---- > 1: {
    ; ---- > 2:       n = 0;
    lvalue n
    push 0
    store 
    ; ---- > 3:       do
    ; ---- > 4:       {
label 17
    ; ---- > 5:         print("\nEnter size of box > ");
    print "\nEnter size of box > "
    ; ---- > 6:         n = read; 
    lvalue n
        read 
    store 
    ; ---- > 7:         print("\n");
    print "\n"
    ; ---- > 8:         for (i = 0; i < n; i++) 
    lvalue i
    push 0
    store 
label 18
    rvalue i
    rvalue n
    < 
    gotrue 20
    goto 19
label 21
    rvalue i
    lvalue i
    rvalue i
    inc 
    store 
    pop 
    goto 18
    ; ---- > 9:         {
label 20
    ; ---- > 10:               m = n;
    lvalue m
    rvalue n
    store 
    ; ---- > 11:               while (m>0)
label 22
    rvalue m
    push 0
    > 
    ; ---- > 12:               {
    gofalse 23
        ; ---- > 13:                      if ((i+m)%2 == 0) print("#");
        rvalue i
        rvalue m
        + 
        push 2
        mod 
        push 0
        = 
        gofalse 24
        print "#"
        ; ---- > 14:                      else print(".");
        goto 25
    label 24
        print "."
        ; ---- > 15:                      m--;
    label 25
        ; ---- 
        rvalue m
        lvalue m
        rvalue m
        dec 
        store 
        pop 
        ; ---- > 16:               }
    ; ---- > 17:               print("\n");
    goto 22
label 23
    ; ---- 
    print "\n"
    ; ---- > 18:         }
    ; ---- > 19:       } while (n>0);
    goto 21
label 19
    rvalue n
    push 0
    > 
    gotrue 17
    ; ---- > 20: }
