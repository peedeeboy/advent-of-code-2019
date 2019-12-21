-- Functions
local function getInput()
    local file = assert(io.open("input.txt", "r"))
    local data = file:read("*all")
    file:close()
    return data
end

local function find_layer_with_least_zeros(table)
    local curMax = 2000
    local layer = 0

    for i = 1, 100 do
        if table[i] < curMax then
            curMax = table[i]
            layer = i
        end
    end

    return layer
end


-- Program
-- Open file
local data = getInput()

-- Variables
local width = 25
local height = 6
local layerSize = width * height
local noPixels = 15000
local noLayers = noPixels / layerSize

local noZeros = {}
local noOnes = {}
local noTwos = {}
local curLayer = 1

for layer = 1, noLayers + 1 do
    noZeros[layer] = 0
end

for layer = 1, noLayers + 1 do
    noOnes[layer] = 0
end

for layer = 1, noLayers + 1 do
    noTwos[layer] = 0
end

-- Iterate through all pixels
for pixel = 1, #data do

    local curPixel = string.sub(data, pixel, pixel)
    if curPixel == '0' then noZeros[curLayer] = noZeros[curLayer] + 1 end
    if curPixel == '1' then noOnes[curLayer] = noOnes[curLayer] + 1 end
    if curPixel == '2' then noTwos[curLayer] = noTwos[curLayer] + 1 end
    
    if pixel % layerSize == 0 then curLayer = curLayer + 1 end

end

local layerWithLeastZeros = find_layer_with_least_zeros(noZeros)
print(noOnes[layerWithLeastZeros] * noTwos[layerWithLeastZeros])
