# from O'Reilly's "Raspberry Pi Cookbook" by Simon Monk  @simonmonk2

import RPi.GPIO as GPIO
import time
import sys


buzzer_pin=int(sys.argv[1])
#pitch=float(sys.argv[2])
#duration=float(sys.argv[3])

GPIO.setmode(GPIO.BCM)
GPIO.setup(buzzer_pin,GPIO.OUT)




def buzz(pitch, duration):
    period = 1.0 / pitch
    delay = period / 2
    cycles = int(duration * pitch)
    for i in range(cycles):
        GPIO.output(buzzer_pin, True)
        time.sleep(delay)
        GPIO.output(buzzer_pin, False)
        time.sleep(delay)




while True:
    input=raw_input()
    input_arr= input.split()
    pitch=float(input_arr[0])
    duration=float(input_arr[1])
    buzz(pitch, duration)
