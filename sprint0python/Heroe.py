
class Heroe:
    def __init__(self, nombre):
       # se inicializa atributos
        self.nombre = nombre
        self.ataque = 19
        self.defensa = 9
        self.salud = 100
        self.salud_maxima = 100
        self.defensa_temporal = 0

# calcular daño
    def atacar(self, enemigo):
        daño = self.ataque - enemigo.defensa
        if daño > 0:
            enemigo.salud -= daño
            print("Héroe ataca a ",enemigo.nombre)
            print("El enemigo ",enemigo.nombre," ha recibido ",daño," puntos de daño.")
        else:
            print("El enemigo ha bloqueado el ataque.")

#el heroe se cura
    def curarse(self):
        cantidad_cura = 20  # Cantidad fija de puntos de salud que se recuperan
        self.salud = min(self.salud + cantidad_cura, self.salud_maxima)
        print("Héroe se ha curado. Salud actual: ",self.salud)

#aumento de defensa temporalmente
    def defenderse(self):
        self.defensa_temporal = 5
        print(f"Héroe se defiende. Defensa aumentada temporalmente a {self.defensa + self.defensa_temporal}.")

#reseteamos la defensa temporal
    def reset_defensa(self):
        self.defensa_temporal = 0
        print(f"La defensa de {self.nombre} vuelve a la normalidad.")

#iramos si sigue vivo
    def esta_vivo(self):
        return self.salud > 0
