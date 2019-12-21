-- Functions
local function getInput()
    local file = assert(io.open("input.txt", "r"))
    local data = file:read("*all")
    file:close()
    return data
end

local function print_layer(table, tableWidth, tableHeight)
    local curLocation = 1

    for h = 1, tableHeight do

        for w = 1, tableWidth do
            local output = ' '
            if table[curLocation] == 1 then output = 'X' end
            io.write(output)
            curLocation = curLocation + 1
        end
        io.write("\n")
    end
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

local flattenedImage = {}
local curLocation = 1

for ls = 1, layerSize do
    flattenedImage[ls] = 2
end

-- Iterate through all pixels
for pixel = 1, #data do

    local curPixel = tonumber(string.sub(data, pixel, pixel))
    if flattenedImage[curLocation] == 2 then flattenedImage[curLocation] = curPixel end
    
    if pixel % layerSize == 0 then curLocation = 1 else curLocation = curLocation + 1 end

end

print_layer(flattenedImage, width, height)
