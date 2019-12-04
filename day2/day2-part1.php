<?php

// Variables
$curPos = 0;
$curInstruction = 0;
$processing = true;

// Load file contents
$instructions = explode(",", file_get_contents("input.txt"));

// Manual fixes
$instructions[1] = 12;
$instructions[2] = 2;

// Processing loop
while($processing) {
    $curInstruction = $instructions[$curPos];
    
    switch($curInstruction) {
        case 1:
            opAdd($instructions, $curPos);
            break;
        case 2:
            opMultiply($instructions, $curPos);
            break;
        case 99:
            $processing = false;
            break;
        default:
            echo "Invalid OpCode";
    }
    
    $curPos = $curPos + 4;
}

// Print program result
print_r($instructions);
echo "Result: " . $instructions[0];

function opAdd(&$instructions, $pos) {
    $p1 = $instructions[$pos + 1];
    $p2 = $instructions[$pos + 2];
    $result = $instructions[$p1] + $instructions[$p2];
    $resultLocation = $instructions[$pos + 3];
    
    $instructions[$resultLocation] = $result;
}

function opMultiply(&$instructions, $pos) {
    $p1 = $instructions[$pos + 1];
    $p2 = $instructions[$pos + 2];
    $result = $instructions[$p1] * $instructions[$p2];
    $resultLocation = $instructions[$pos + 3];
    
    $instructions[$resultLocation] = $result;
}