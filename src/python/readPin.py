import RPi.GPIO as GPIO
import sys


GPIO.setmode(GPIO.BCM)

pin=int(sys.argv[1])

GPIO.setup(pin,GPIO.IN, pull_up_down=GPIO.PUD_UP)

print GPIO.input(pin)
