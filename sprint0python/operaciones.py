

def suma(n1, n2):
    # Esta función imprime la suma de dos números
    print("suma", n1 + n2)

def resta(n1, n2):
    # Esta función imprime la resta de dos números
    print("resta", str(n1 - n2))

def multiplicacion(n1, n2):
    # Esta función imprime la multiplicación de dos números
    print("multiplicacion", str(n1 * n2))

def division(n1, n2):
    # Esta función imprime la división de dos números
    # Si el divisor es cero, imprime un mensaje de error
    if n2 == 0:
        print("Error: División por cero no permitida.")
    else:
        print("division", n1 / n2)

# Llamadas a las funciones con ejemplos
suma(2, 5)
resta(2, 5)
multiplicacion(2, 5)
division(2, 5)