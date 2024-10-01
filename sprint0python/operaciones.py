
def suma(n1,n2):
   print ("suma",n1+n2)

def resta(n1,n2):
    print("resta",str(n1-n2))

def multiplicacion(n1, n2):
   print("multiplicacion" ,str(n1 * n2))

def division(n1, n2):
    if n2 == 0:
        print("Error: División por cero no permitida.")
    else:
        print("division", n1 / n2)

suma(2, 5)
resta(2, 5)
multiplicacion(2, 5)
division(2, 5)
