# Importaciones
from Heroe import Heroe
from Mazmorra import Mazmorra


def main():
    #dar nombre al heroe
    nombre_heroe = input("Introduce el nombre de tu heroe; ")
    heroe = Heroe(nombre_heroe)

    mazmorra = Mazmorra(heroe)
    #iniciar juego
    mazmorra.jugar()


if __name__ == "__main__":
    main()
