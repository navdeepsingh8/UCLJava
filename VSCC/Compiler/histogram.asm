    ; ---- > 1: {
    ; ---- > 2:         print("Enter histogram values on one line, last value 0 > "); 
    print "Enter histogram values on one line, last value 0 > "
    ; ---- > 3:         n = read;
    lvalue n
        read 
    store 
    ; ---- > 4:         while (n>0) 
label 17
    rvalue n
    push 0
    > 
    ; ---- > 5:         {
    gofalse 18
        ; ---- > 6:               print("\n");
        print "\n"
        ; ---- > 7:               while (n>0)
    label 19
        rvalue n
        push 0
        > 
        ; ---- > 8:               {
        gofalse 20
            ; ---- > 9:                      print("#");
            print "#"
            ; ---- > 10:                      n--;
            rvalue n
            lvalue n
            rvalue n
            dec 
            store 
            pop 
            ; ---- > 11:               }
        ; ---- > 12:               n = read;
        goto 19
    label 20
        ; ---- 
        lvalue n
            read 
        store 
        ; ---- > 13:         }
    ; ---- > 14:         print("\n");
    goto 17
label 18
    ; ---- 
    print "\n"
    ; ---- > 15: }
histogram.asm

Page 1 of 1

