<?php

// Load file contents
$instructions = explode(",", file_get_contents("input.txt"));

// Main loop
for($noun = 0; $noun <= 99; $noun++) {
    
    for($verb = 0; $verb <= 99; $verb++) {
        
        $computer = new computer();
        $result = $computer->process($instructions, $noun, $verb);
        
        if($result === 19690720) {
            echo "Corect result found!" . PHP_EOL;
            echo "Noun: " . $noun . PHP_EOL;
            echo "Verb: " . $verb . PHP_EOL;
            echo "Result: " . (100 * $noun + $verb);
        }
    }
    
}


class computer {
    
    public function process($instructions, $noun, $verb) {
        
        $curPos = 0;
        $curInstruction = 0;
        $processing = true;
        
        // Fixes
        $instructions[1] = $noun;
        $instructions[2] = $verb;

        while($processing) {
            $curInstruction = $instructions[$curPos];

            switch($curInstruction) {
                case 1:
                    $this->opAdd($instructions, $curPos);
                    break;
                case 2:
                    $this->opMultiply($instructions, $curPos);
                    break;
                case 99:
                    $processing = false;
                    break;
                default:
                    $processing = false;
                    echo "Invalid OpCode";
            }

            $curPos = $curPos + 4;
        }
        
        return $instructions[0];
    }
    
    public function opAdd(&$instructions, $pos) {
        $p1 = $instructions[$pos + 1];
        $p2 = $instructions[$pos + 2];
        $result = $instructions[$p1] + $instructions[$p2];
        $resultLocation = $instructions[$pos + 3];

        $instructions[$resultLocation] = $result;
    }

    public function opMultiply(&$instructions, $pos) {
        $p1 = $instructions[$pos + 1];
        $p2 = $instructions[$pos + 2];
        $result = $instructions[$p1] * $instructions[$p2];
        $resultLocation = $instructions[$pos + 3];

        $instructions[$resultLocation] = $result;
    }
}




