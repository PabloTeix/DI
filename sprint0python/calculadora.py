from operaciones import suma, resta, multiplicacion, division

def main():
    while True:
        # Solicita al usuario que introduzca dos números
        n1 = int(input("Introduce el primer numero: "))
        n2 = int(input("Introduce el segundo numero: "))

        # Muestra las opciones de operación disponibles
        print("Elige la operación:")
        print("1. Suma")
        print("2. Resta")
        print("3. Multiplicacion")
        print("4. Division")

        # Solicita al usuario que elija una operación
        operacion = input("Elige opción: ")

        # Ejecuta la operación seleccionada
        if operacion == '1':
            suma(n1, n2)
        elif operacion == '2':
            resta(n1, n2)
        elif operacion == '3':
            multiplicacion(n1, n2)
        elif operacion == '4':
            division(n1, n2)
        else:
            print("Opción no disponible")

        # Pregunta al usuario si desea realizar otra operación
        continuar = input("¿Deseas realizar otra operación? (s/n): ")
        if continuar.lower() != 's':
            print("Programa finalizado.")
            break

if __name__ == "__main__":
    main()