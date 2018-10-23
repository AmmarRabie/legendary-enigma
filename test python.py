dictionary = {"K2":'ammar',"K1":'fkldgm',"K3":5}
try:
    a = dictionary['K1']
    print(a)
    b = dictionary['K4']
    print(b)

except KeyError:
    print("invalid input")

print(text("SELECT my_mutating_procedure()"))