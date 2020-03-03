# LC3 Subroutine Boilerplate Generator
A simple subroutine boilerplate generator to help simplify your workflow (even just a bit).

## How to use
1. Make sure to have `java` and `git` installed.
2. Run the following command in terminal
```shell
cd /tmp && curl https://raw.githubusercontent.com/JiachenRen/lc3-subroutine-generator/master/src/Generator.java > Generator.java && javac Generator.java && java Generator
```
3. Follow the instructions. 
4. When you are done, results are automatically copied to clipboard.

## Sample boilerplate
```nasm
MULT ADD	R6, R6, -4	; Allocate space rv,ra,fp,lv1
STR	R7, R6, 2	; Save Ret Addr
STR	R5, R6, 1	; Save Old FP
ADD	R5, R6, 0	; Copy SP to FP
ADD	R6, R6, -7	; Make room for R0-R4 and local vars
; Save R0 - R4
STR	R0, R5, -3	; Save R0
STR	R1, R5, -4	; Save R1
STR	R2, R5, -5	; Save R2
STR	R3, R5, -6	; Save R3
STR	R4, R5, -7	; Save R4
; ============== BEGIN YOUR WORK ==============

; Uncomment the following to access arguments
; LDR	R0, R5, 4	; Load 1 argument (from left) into R0
; LDR	R1, R5, 5	; Load 2 argument (from left) into R1
; LDR	R2, R5, 6	; Load 3 argument (from left) into R2

; Uncomment the following to use local variables
; STR	X, R5, 0	; Store value of X into LV 1
; STR	X, R5, 1	; Store value of X into LV 2
; STR	X, R5, 2	; Store value of X into LV 3

; Uncomment the following to set return value
; STR	X, R5, 3	; Set return value to memory addr specified by X
; ============== END YOUR WORK ==============
; Restore R0 - R4
LDR	R0, R5, -3	; Restore R0
LDR	R1, R5, -4	; Restore R1
LDR	R2, R5, -5	; Restore R2
LDR	R3, R5, -6	; Restore R3
LDR	R4, R5, -7	; Restore R4
ADD	R6, R5, 0	; Pop local vars & saved regs
LDR	R7, R5, 2	; R7 = RA
LDR	R5, R5, 1	; FP = OldFP
ADD	R6, R6, 3	; Pop 3 wds, R6 now rests on RV
RET
```