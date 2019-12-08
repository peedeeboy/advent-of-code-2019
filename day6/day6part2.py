# Functions
def read_input():
    input_file = open("input.txt", "r")
    file_lines = input_file.readlines()
    input_file.close()

    return [line.strip() for line in file_lines]


def create_planet_list(raw_planets):
    items = dict()
    for line in raw_planets:
        arr = line.split(")")
        items[arr[1]] = arr[0]

    return items


def calculate_orbits(planet, planets, agg=0):
    if planet == "COM":
        return agg
    else:
        return calculate_orbits(planets[planet], planets, agg=agg + 1)


def sum_orbits(planets):
    total = 0
    for planet in planets:
        total = total + calculate_orbits(planet, planets)

    return total


def map_path(planet, planets, agg):
    if planet == "COM":
        return agg
    else:
        agg.add(planets[planet])
        return map_path(planets[planet], planets, agg)


# Program
lines = read_input()
planet_list = create_planet_list(lines)

you = map_path("YOU", planet_list, set())
san = map_path("SAN", planet_list, set())

intersection = you.intersection(san)

you = you - intersection
san = san - intersection

print(len(you) + len(san))
