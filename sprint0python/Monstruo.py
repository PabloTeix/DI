class Monstruo:
    def __init__(self, nombre, ataque, defensa, salud):
 #iniciamos valores
        self.nombre = nombre
        self.ataque = ataque
        self.defensa = defensa
        self.salud = salud

#calculamos daño al heroe
    def atacar(self, heroe):
        daño = self.ataque - heroe.defensa
        if daño > 0:
            heroe.salud -= daño
            print(f"El monstruo {self.nombre} ataca a {heroe.nombre}.")
            print(f"El héroe {heroe.nombre} ha recibido {daño} puntos de daño.")
        else:
            print(f"El héroe ha bloqueado el ataque.")

#miramos si isgue vivo
    def esta_vivo(self):
        return self.salud > 0