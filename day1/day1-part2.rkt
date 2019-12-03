#lang racket
(define (calculate-fuel f [acc 0])
  (let ((newfuel (- (floor (/ f 3)) 2)))
  (if (< newfuel 1)
      acc
      (calculate-fuel newfuel (+ newfuel acc)))))

(apply + (map (lambda (f)
                (calculate-fuel f))
              (map (lambda (i)
                     (string->number i))
                   (file->lines "input.txt"))))