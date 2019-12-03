#lang racket
(apply + (map (lambda (i)
                (- (floor (/ i 3)) 2))
              (map (lambda (i)
                     (string->number i))
                   (file->lines "input.txt"))))