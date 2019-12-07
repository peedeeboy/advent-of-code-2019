% Facts
digit(0).
digit(1).
digit(2).
digit(3).
digit(4).
digit(5).
digit(6).
digit(7).
digit(8).
digit(9).

%Rules
two_adjacent_digits_are_the_same(D1, D2, D3, D4, D5, D6) :-
    D1 == D2, D2 =\= D3; 
    D1 =\= D2, D2 == D3, D3 =\= D4; 
    D2 =\= D3, D3 == D4, D4 =\= D5; 
    D3 =\= D4, D4 == D5, D5 =\= D6; 
    D4 =\= D5, D5 == D6.

greater_than_equal_to_138307(D1, D2, D3, D4, D5, D6) :-
    (D6 + (D5 * 10) + (D4 * 100) + (D3 * 1000) + (D2 * 10000) + (D1 * 100000)) >= 138307.

less_than_equal_to_654504(D1, D2, D3, D4, D5, D6) :-
    (D6 + (D5 * 10) + (D4 * 100) + (D3 * 1000) + (D2 * 10000) + (D1 * 100000)) =< 654504.

going_from_left_to_right_the_digits_never_decrease(D1, D2, D3, D4, D5, D6) :-
    D1 =< D2, D2 =< D3, D3 =< D4, D4 =< D5, D5 =< D6.

code(D1, D2, D3, D4, D5, D6) :-
    digit(D1),
    digit(D2),
    digit(D3),
    digit(D4),
    digit(D5),
    digit(D6),
    greater_than_equal_to_138307(D1, D2, D3, D4, D5, D6),
    less_than_equal_to_654504(D1, D2, D3, D4, D5, D6),
    two_adjacent_digits_are_the_same(D1, D2, D3, D4, D5, D6),
    going_from_left_to_right_the_digits_never_decrease(D1, D2, D3, D4, D5, D6).

solve(Result) :-
    aggregate_all(count, [D1,D2,D3,D4,D5,D6], code(D1,D2,D3,D4,D5,D6), Result).

% Query
?- solve(Result), write(Result), nl.