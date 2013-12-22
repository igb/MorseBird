import RPi.GPIO as GPIO
import sys


GPIO.setmode(GPIO.BCM)

pinA=int(sys.argv[1])
pinB=int(sys.argv[2])

GPIO.setup(pinA,GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(pinB,GPIO.IN, pull_up_down=GPIO.PUD_UP)

pin_A_state=None
pin_B_state=None

while True:
    tmp_A_state=GPIO.input(pinA)
    tmp_B_state=GPIO.input(pinB)

    if (tmp_A_state != pin_A_state):

        pin_A_state=tmp_A_state


        print "A " + str(pin_A_state)
        print "B " + str(pin_B_state)


    if (tmp_B_state != pin_B_state):

        pin_B_state=tmp_B_state


        print "A " + str(pin_A_state)
        print "B " + str(pin_B_state)


    sys.stdout.flush()

