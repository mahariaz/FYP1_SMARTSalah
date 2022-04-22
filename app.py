import csv

from flask import Flask,request,jsonify
import pickle
import numpy as np
import statistics
from statistics import mode
from flask import send_file

model = pickle.load(open('Model.pkl','rb'))

app = Flask(__name__)

posture_order={}
file_val = {}
pos = {}
total_rakah=0
@app.route('/')
def index():
    return "Hello world"

@app.route('/predict',methods=['POST'])
def predict():

    x = request.form.get('x')
    y = request.form.get('y')
    z = request.form.get('z')

    input_query = np.array([[x, y, z]])

    result = model.predict(input_query)[0]


    return jsonify({'posture':result})

@app.route('/val',methods=['POST'])
def val():
    num=request.form.get('num')
    filename="file"+str(num)+".csv"
    # print(filename)
    i=1
    with open(filename,"r") as f:
        for line in csv.DictReader(f):
            x=line['x']
            y=line['y']
            z=line['z']

            input_query = np.array([[x, y, z]])

            result = model.predict(input_query)[0]
            newpos = {i: {'Timestamp':line['Timestamp'],'Posture': result,'Type':line['Type'],'Name':line['Name'],'Rakah':line['Rakah']}}
            #newpos = { i: { 'Timestamp': line['Timestamp'],'Posture': result}}

            total_rakah=line['Rakah']
            Name=line['Name']
            type=line['Type']
            # type=line['Type']
            # Name=line['Name']
            pos.update(newpos)
            i+=1
    val=7
    freq=[]
    time=[]
    p=""
    for q in range(0,len(pos),7):

        first_key = list(pos.values())[q:val]

        a_key = "Posture"
        values_of_key = [a_dict[a_key] for a_dict in first_key]
        # print(values_of_key)
        # print(values_of_key.index(mode(values_of_key))+q)
        if p!=mode(values_of_key):
            v=values_of_key.index(mode(values_of_key))+q
            # print(first_key[values_of_key.index(mode(values_of_key))]['Timestamp'])
            p=mode(values_of_key)
            freq.append(p)
            time.append(first_key[values_of_key.index(mode(values_of_key))]['Timestamp'])

        val+=7

    rakah_count = 0


    posture = ""
    sajda_value = 0
    with open('file4.csv','w') as f:
        writer=csv.writer(f)
        row=['Time','Posture','TotalRakah','Name','type']
        writer.writerow(row)

        for i in range(len(freq)):
            if freq[i]=="Qayam" and posture=="":
                posture=freq[i]
                rakah_count+=1
                newpos
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                #neworder = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row=[time[i],freq[i],total_rakah,Name,type]
                writer.writerow(row)
                posture_order.update(newpos)

            if posture=="Qayam" and freq[i]=="Ruku":
                posture=freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}

                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)

            # elif posture=="Qayam" and freq[i]=="Qoum":
            #     posture=freq[i]
            #     neworder={i:{'Type':type,'Name':Name,'total_rakah':total_rakah,'posture':freq[i],'time':time[i]}}
            #     row = [time[i], freq[i], total_rakah, Name, type]
            #     writer.writerow(row)
            #     posture_order.update(neworder)

            elif posture=="Ruku" and freq[i]=="Qoum":
                posture = freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                # neworder = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)

            elif posture=="Qoum" and freq[i]=="Sajda":
                sajda_value=1
                posture = freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                # neworder = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)

            elif posture=="Sajda" and freq[i]=="Jalsa" and i!=len(freq)-2:
                posture = freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                # newpos = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)


            elif posture=="Jalsa" and freq[i]=="Sajda":
                sajda_value=2
                posture = freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                # neworder = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)

            elif posture=="Jalsa" and freq[i]=="Qayam" and (rakah_count==1 or rakah_count==3):
                posture = freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                # neworder = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)

            elif posture=="Sajda" and freq[i]=="Qayam" and sajda_value==2:
                sajda_value=0
                rakah_count+=1
                posture = freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                # neworder = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)
            # elif posture=="Sajda" and freq[i]=="Sajda":
            #     posture = freq[i]
            #     neworder = {i: {'posture': freq[i], 'total_rakah': total_rakah}}
            #     posture_order.update(neworder)

            elif posture=="Sajda" and freq[i]=="Tashahud" and (rakah_count==2 or rakah_count==4 or rakah_count==3) and sajda_value==2:

                posture = freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                # neworder = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)

            elif posture=="Tashahud" and freq[i]=="Qayam":
                sajda_value=0
                rakah_count+=1
                posture = freq[i]
                newpos = {i: {'Timestamp':time[i],'Posture': freq[i],'Type':type,'Name':Name,'Rakah':total_rakah}}
                # neworder = {i: {'Timestamp': line['Timestamp'], 'Posture': result}}
                row = [time[i], freq[i], total_rakah, Name, type]
                writer.writerow(row)
                posture_order.update(newpos)


    # print(posture_order)
    return jsonify(posture_order)
    # return send_file('file4.csv')
@app.route('/getValues',methods=['POST'])
def getValues():
    x=request.form.get('x')
    y = request.form.get('y')
    z = request.form.get('z')

    result={'x':x,'y':y,'z':z}
    return jsonify(result)

@app.route('/reasoner',methods=['GET'])
def reasoner():
    rakahs=2
    pos_order=[]
    with open("file4.csv","r") as f:
        for line in csv.DictReader(f):
            rakahs=line['TotalRakah']
            pos_order.append(line['Posture'])
    reasoner={}

    # rakah = list(posture_order.values())[0:1]
    # a_key = "total_rakah"
    # values_of_key = [a_dict[a_key] for a_dict in rakah]
    # temp = list(posture_order.values())[0:len(posture_order)]
    # a_key = "posture"
    # pos_order = [a_dict[a_key] for a_dict in temp]
    rakah1=0
    rakah2=0
    rakah3=0
    rakah4=0
    extra=0
    #values_of_key[0]=2
    #pos_order=['Qayam','Ruku','Qoum','Sajda','Jalsa','Sajda','Qayam','Ruku','Qoum','Sajda','Jalsa','Sajda','Tashahud']
    # print(pos_order)
    t1 = 0
    enomali=0
    for i in range(1, 5):

        if i>int(rakahs):

            if t1<len(pos_order) and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[
                t1 + 6] == "Tashahud":
                newstats = {i: {"Rakah": i, "Status": "Error", 'PostureMiss': "None", 'PostureExtra': "None",
                                "RakahExtra": "Yes"}}
                reasoner.update(newstats)
                 #print("Extra Rakah")
            break
        if i == 1 or i == 3:
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[t1 + 6] == "Qayam":
                if i == 1:
                    rakah1 = 1
                if i == 3:
                    rakah3 = 1
                #print("rakah 1 complete")
                newstats = {i:{"Rakah":i,"Status":"tic",'PostureMiss':"None",'PostureExtra':"None","RakahExtra":"None"}}
                reasoner.update(newstats)

                # print("Rakah", i, "complete")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[t1 + 6] == "Jalsa" and pos_order[t1 + 7]=="Sajda":

                #print("rakah 1 complete")
                newstats = {i:{"Rakah":i,"Status":"Error",'PostureMiss':"None",'PostureExtra':"Sajda","RakahExtra":"None"}}
                reasoner.update(newstats)
                extra=1


            if int(rakahs)==3 and i==3 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[t1 + 6] == "Tashahud":
                newstats = {i:{"Rakah": i, "Status": "tic",'PostureMiss':"None",'PostureExtra':"None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "complete")

            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Qoum" and pos_order[t1 + 2] == "Sajda" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Ruku",'PostureExtra':"None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Ruku Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Qayam":
                newstats = {i: {"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda2", 'PostureExtra': "None",
                                "RakahExtra": "None"}}
                reasoner.update(newstats)

                enomali=1
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Qoum" and pos_order[t1 + 2] == "Sajda" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1+5]=="Sajda" and pos_order[t1+6]=="Tashahud":
                newstats = {i:{"Rakah": i, "Status": "Error", 'PostureMiss': "None",'PostureExtra':"Sajda","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Sajda Extra")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Qayam":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda2", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Sajda2 Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Sajda" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Qoum", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Qoum Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda1", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Sajda1 Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Sajda":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Jalsa", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Jalsa Missed")

        if i == 1 and rakah1 == 1:
            t1 = 6
        if i == 1 and rakah1 == 0 and enomali==1:
            t1=4
            enomali=0
        if i == 1 and rakah1 == 0:
            t1 = 5
        if i==1 and extra==1:
            t1=8
            extra=0

        if i == 2 or i == 4:

            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[
                t1 + 6] == "Tashahud":
                print("Rakah 2 complete")
                if i == 2:
                    rakah2 = 1
                if i == 4:
                    rakah4 = 1
                newstats = {i:{"Rakah": i, "Status": "tic", 'PostureMiss': "None", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "complete")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Qoum" and pos_order[t1 + 2] == "Sajda" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Tashahud":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Ruku", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Ruku Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[
                t1 + 6] == "Jalsa" and pos_order[t1 + 7] == "Sajda":
                # print("rakah 1 complete")
                newstats = {i: {"Rakah": i, "Status": "Error", 'PostureMiss': "None", 'PostureExtra': "Sajda",
                                "RakahExtra": "None"}}
                reasoner.update(newstats)
                extra = 1

            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Tashahud":
                newstats = {i: {"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda2", 'PostureExtra': "None",
                                "RakahExtra": "None"}}
                reasoner.update(newstats)
                enomali=1
            #     print("RRRRRRRRRakahaaa")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Sajda" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Tashahud":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Qoum", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Qoum Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Tashahud":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda1", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Sajda1 Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Tashahud":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Jalsa", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Jalsa Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Tashahud":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda2", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Sajda2 Missed")
            if pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[
                t1 + 6] == "Qayam":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Tashahud", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                # print("Rakah", i, "Incomplete")
                # print("Posture: Tashahud Missed")
        if i == 2 and rakah1 == 1 and rakah2 == 1:
            t1 = 13
        if i==2 and rakah1 == 1 and rakah2==0 and enomali==1:
            t1=11
            enomali=0
        if (i == 2 and rakah1 == 1 and rakah2 == 0) or (i == 2 and rakah1 == 0 and rakah2 == 1):
            t1 = 12
        if i == 2 and rakah1 == 0 and rakah2 == 0:
            t1 = 11
        if i == 2 and rakah1 == 0 and rakah2 == 0 and enomali==1:
            t1=9
            enomali=0
        if i==2 and extra==1 and rakah1==1:
            t1=14
        if i == 3 and rakah1 == 1 and rakah2 == 1 and rakah3 == 1:
            t1 = 19

        if (i == 3 and rakah1 == 0 and rakah2 == 1 and rakah3 == 1) or (
                i == 3 and rakah1 == 1 and rakah2 == 0 and rakah3 == 1) or (
                i == 2 and rakah1 == 0 and rakah2 == 1 and rakah3 == 0):
            t1 = 18
        if i == 3 and rakah1 == 0 and rakah2 == 0 and rakah3 == 0:
            t1 = 17


    return jsonify(reasoner)

if __name__ == '__main__':
    app.run(debug=True)

#https://tizenflask.herokuapp.com/getValues