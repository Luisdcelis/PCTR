import multiprocessing
import time

shared_cont = 0

def myProcess(iter):
    global shared_cont
    for cont in range(iter):
        shared_cont+=1

if __name__ == "__main__":
    myProcs =  []
    nproc = int(input("tasks?"))
    iters = int(input("iteations?"))
    start = time.time()

    for i in range(nproc):
        proc = multiprocessing.Process(target=myProcess,args=(iters,))
        myProcs.append(proc)

    for i in myProcs : i.start()

    for i in myProcs : i.join()
    
    end = time.time()
    print(shared_cont)
    print(shared_cont+1)
    print('Como explica el output mostrado?')