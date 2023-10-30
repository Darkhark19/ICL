.class public Header
.super java/lang/Object
;
; standard initializer
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V

       ; set limits used by this method
       .limit locals  1000
       .limit stack 25600

       ; setup local variables:

       ;    1 - the PrintStream object held in java.lang.System.out

       ; getstatic java/lang/System/out Ljava/io/PrintStream;

       ; place bytecodes here



; START


new frame_0
dup
invokespecial frame_0/<init>()V
astore_3
aload_3
new ref_of_int
dup
invokespecial ref_of_int/<init>()V
dup
sipush 5666
putfield ref_of_int/v I
putfield frame_0/v0 Lref_of_int;
L0:
aload_3
getfield frame_0/v0 Lref_of_int;
getfield ref_of_int/v I
sipush 1
isub
ifgt L2
sipush 0
goto L3
L2: 
sipush 1
L3: 
ifeq L1
new frame_1
dup
invokespecial frame_1/<init>()V
dup
aload_3
putfield frame_1/sl Lframe_0;
astore_3
sipush 2
aload_3
getfield frame_1/sl Lframe_0;
getfield frame_0/v0 Lref_of_int;
getfield ref_of_int/v I
sipush 2
idiv
imul
aload_3
getfield frame_1/sl Lframe_0;
getfield frame_0/v0 Lref_of_int;
getfield ref_of_int/v I
isub
ifeq L6
sipush 0
goto L7
L6: 
sipush 1
L7: 
ifeq L4
aload_3
getfield frame_1/sl Lframe_0;
getfield frame_0/v0 Lref_of_int;
aload_3
getfield frame_1/sl Lframe_0;
getfield frame_0/v0 Lref_of_int;
getfield ref_of_int/v I
sipush 2
idiv
putfield ref_of_int/v I
goto L5
L4:
aload_3
getfield frame_1/sl Lframe_0;
getfield frame_0/v0 Lref_of_int;
sipush 3
aload_3
getfield frame_1/sl Lframe_0;
getfield frame_0/v0 Lref_of_int;
getfield ref_of_int/v I
imul
sipush 1
iadd
putfield ref_of_int/v I
L5:
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_3
getfield frame_1/sl Lframe_0;
getfield frame_0/v0 Lref_of_int;
getfield ref_of_int/v I
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload_3
getfield frame_1/sl Lframe_0;
astore_3
goto L0
L1:
aload_3
getfield frame_0/sl Ljava/lang/Object;
astore_3


; END

        ; convert to String;
        ; invokestatic java/lang/String/valueOf(I)Ljava/lang/String;

        ; call println
        ; invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

    return

.end method
