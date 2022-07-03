import csv

import os
from os.path import join, dirname, realpath
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

APP_ROOT = os.path.dirname(os.path.abspath(__file__))

@app.route('/')
def index():
    return "Hello world"

@app.route('/uploader', methods = ['POST', 'PUT'])
def upload_file():
    #device_name = request.headers.get('Device')
    file_name = "file1.csv"
    with open(file_name, 'wb') as f:
        f.write(request.stream.read())
    return 'file uploaded successfully'

@app.route('/getfile',methods=['POST'])
def getfile():

    f = request.files['']

    f.save(os.path.join(APP_ROOT, f.filename))

    i = 1

    with open('file1.csv','w') as fs:
        writer = csv.writer(fs)
        row = ['Type', 'Name', 'Rakah', 'x','y','z', 'Timestamp']
        writer.writerow(row)
        with open(f.filename, "r") as f:
            for line in csv.DictReader(f):
                #print("insideeeee")
                x = line['x']
                y = line['y']
                z = line['z']
                time = line['Timestamp']
                rkh = line['Rakah']
                nme = line['Name']
                typ = line['Type']
                row = [typ, nme, rkh, x, y, z, time]
                writer.writerow(row)

    return "succesful"

@app.route('/kgld',methods=['POST'])
def kgld():
    i=1
    file = open('file4.csv',"r")
    content = file.readlines()
    ls = content[2].split(",")
    #print(ls)
    Time=ls[0]
    person_id = 'mahaRiaz'
    salah="S1"
    SalahUnit=""
    Salah_Name = ls[3]
    TotalRakah=ls[2]
    sunit = ls[4].split("\n")
    SalahUnit_Type = sunit[0]
    if sunit[0]=='Farz':
        SalahUnit='FZ'
    elif sunit[0]=='Sunnah':
        SalahUnit='SN'
    elif sunit[0]=='Nafal':
        SalahUnit='NF'
    ls=content[len(content) - 2].split(",")
    #print(ls)
    Etime=ls[0]
    RakahNumber=0
    SajdaNumber=1
    extraSajda=0
    with open('data.csv',"w") as d:
        writer = csv.writer(d)
        row=['person_id','StartTime','Postures','Total_Rakah','Salah_Name','RakahNumber','Rakah','Salah','SalahUnit_Type','SalahUnit'
        ,'Time','Etime','PosturesName','UnitRakah']

        writer.writerow(row)
        with open('file4.csv',"r") as f:
            for line in csv.DictReader(f):
                StartTime=line['Time']
                PosturesName=line['Posture']
                if line['Posture']=="Qayam":
                    RakahNumber+=1
                    Rakah=SalahUnit + "_rakah" +str(RakahNumber)
                    SajdaNumber=1

                if line['Posture']=="Qayam" and RakahNumber==1:
                    Postures= SalahUnit + "_Rakah1_P1"
                elif line['Posture']=="Ruku" and RakahNumber==1:
                    Postures= SalahUnit + "_Rakah1_P2"
                elif line['Posture']=="Qoum" and RakahNumber==1:
                    Postures= SalahUnit + "_Rakah1_P3"
                elif line['Posture']=="Sajda" and RakahNumber==1 and SajdaNumber==1:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah1_P4"
                elif line['Posture']=="Jalsa" and RakahNumber==1 and SajdaNumber<3:
                    Postures= SalahUnit + "_Rakah1_P5"
                elif line['Posture']=="Sajda" and RakahNumber==1 and SajdaNumber==2:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah1_P6"
                elif line['Posture']=="Jalsa" and RakahNumber==1 and SajdaNumber==3:
                    Postures= SalahUnit + "_Rakah1_P7"
                elif line['Posture']=="Sajda" and RakahNumber==1 and SajdaNumber==3:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah1_P8"
                elif line['Posture']=="Tashahud" and RakahNumber==1:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah1_P7"

                elif line['Posture']=="Qayam" and RakahNumber==2:
                    Postures= SalahUnit + "_Rakah2_P1"
                elif line['Posture']=="Ruku" and RakahNumber==2:
                    Postures= SalahUnit + "_Rakah2_P2"
                elif line['Posture']=="Qoum" and RakahNumber==2:
                    Postures= SalahUnit + "_Rakah2_P3"
                elif line['Posture']=="Sajda" and RakahNumber==2 and SajdaNumber==1:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah2_P4"
                elif line['Posture']=="Jalsa" and RakahNumber==2 and SajdaNumber<3:
                    Postures= SalahUnit + "_Rakah2_P5"
                elif line['Posture']=="Sajda" and RakahNumber==2 and SajdaNumber==2:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah2_P6"
                elif line['Posture']=="Jalsa" and RakahNumber==2 and SajdaNumber==3:
                    Postures= SalahUnit + "_Rakah2_P7"
                elif line['Posture']=="Sajda" and RakahNumber==2 and SajdaNumber==3:
                    extraSajda=1
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah2_P8"
                elif line['Posture']=="Tashahud" and RakahNumber==2 and extraSajda==1:
                    Postures=SalahUnit + "_Rakah2_P9"
                elif line['Posture']=="Tashahud" and RakahNumber==2 and extraSajda==0:
                    Postures= SalahUnit + "_Rakah2_P7"

                elif line['Posture']=="Qayam" and RakahNumber==3:
                    Postures= SalahUnit + "_Rakah3_P1"
                elif line['Posture']=="Ruku" and RakahNumber==3:
                    Postures= SalahUnit + "_Rakah3_P2"
                elif line['Posture']=="Qoum" and RakahNumber==3:
                    Postures= SalahUnit + "_Rakah3_P3"
                elif line['Posture']=="Sajda" and RakahNumber==3 and SajdaNumber==1:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah3_P4"
                elif line['Posture']=="Jalsa" and RakahNumber==3 and SajdaNumber<3:
                    Postures= SalahUnit + "_Rakah3_P5"
                elif line['Posture']=="Sajda" and RakahNumber==3 and SajdaNumber==2:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah3_P6"
                elif line['Posture']=="Jalsa" and RakahNumber==3 and SajdaNumber==3:
                    Postures= SalahUnit + "_Rakah3_P7"
                elif line['Posture']=="Sajda" and RakahNumber==3 and SajdaNumber==3:
                    extraSajda=1
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah3_P8"
                elif line['Posture']=="Tashahud" and RakahNumber==3 and extraSajda==1:
                    Postures= SalahUnit + "_Rakah3_P9"
                elif line['Posture']=="Tashahud" and RakahNumber==3 and extraSajda==0:
                    Postures= SalahUnit + "_Rakah3_P7"

                elif line['Posture']=="Qayam" and RakahNumber==4:
                    Postures= SalahUnit + "_Rakah4_P1"
                elif line['Posture']=="Ruku" and RakahNumber==4:
                    Postures= SalahUnit + "_Rakah4_P2"
                elif line['Posture']=="Qoum" and RakahNumber==4:
                    Postures= SalahUnit + "_Rakah4_P3"
                elif line['Posture']=="Sajda" and RakahNumber==4 and SajdaNumber==1:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah4_P4"
                elif line['Posture']=="Jalsa" and RakahNumber==4 and SajdaNumber<3:
                    Postures= SalahUnit + "_Rakah4_P5"
                elif line['Posture']=="Sajda" and RakahNumber==4 and SajdaNumber==2:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah4_P6"
                elif line['Posture']=="Jalsa" and RakahNumber==4 and SajdaNumber==3:
                    Postures= SalahUnit + "_Rakah4_P7"
                elif line['Posture']=="Sajda" and RakahNumber==4 and SajdaNumber==3:
                    extraSajda=1
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah4_P8"
                elif line['Posture']=="Tashahud" and RakahNumber==4 and extraSajda==1:
                    Postures= SalahUnit + "_Rakah4_P9"
                elif line['Posture']=="Tashahud" and RakahNumber==4 and extraSajda==0:
                    Postures= SalahUnit + "_Rakah4_P7"

                elif line['Posture']=="Qayam" and RakahNumber==5:
                    Postures= SalahUnit + "_Rakah5_P1"
                elif line['Posture']=="Ruku" and RakahNumber==5:
                    Postures= SalahUnit + "_Rakah5_P2"
                elif line['Posture']=="Qoum" and RakahNumber==5:
                    Postures= SalahUnit + "_Rakah5_P3"
                elif line['Posture']=="Sajda" and RakahNumber==5 and SajdaNumber==1:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah5_P4"
                elif line['Posture']=="Jalsa" and RakahNumber==5 and SajdaNumber<3:
                    Postures= SalahUnit + "_Rakah5_P5"
                elif line['Posture']=="Sajda" and RakahNumber==5 and SajdaNumber==2:
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah5_P6"
                elif line['Posture']=="Jalsa" and RakahNumber==5 and SajdaNumber==3:
                    Postures= SalahUnit + "_Rakah5_P7"
                elif line['Posture']=="Sajda" and RakahNumber==5 and SajdaNumber==3:
                    extraSajda=1
                    SajdaNumber+=1
                    Postures= SalahUnit + "_Rakah5_P8"
                elif line['Posture']=="Tashahud" and RakahNumber==5 and extraSajda==1:
                    Postures= SalahUnit + "_Rakah5_P9"
                elif line['Posture']=="Tashahud" and RakahNumber==5 and extraSajda==0:
                    Postures= SalahUnit + "_Rakah5_P7"

                row = [person_id, StartTime, Postures, TotalRakah, Salah_Name, RakahNumber, Rakah,
                       salah,SalahUnit_Type,SalahUnit, Time, Etime,PosturesName,TotalRakah]
                writer.writerow(row)
                #print(person_id, Time, Etime, salah, Salah_Name, SalahUnit_Type, SalahUnit, TotalRakah, RakahNumber,
                #Rakah,StartTime,PosturesName, Postures)

    return "succesful"
@app.route('/val',methods=['POST'])
def val():
    pos={}
    dic1={}
    dic2={}
    cons_arr=[]
    posture_order={}
    i=1
    pos1=""
    count1=0
    count2=0
    qayam=0
    rakah_count=0
    with open('file1.csv',"r") as f:
        for line in csv.DictReader(f):
            x=line['x']
            y=line['y']
            z=line['z']
            rakah_count=line['Rakah']
            input_query = np.array([[x, y, z]])
            result = model.predict(input_query)[0]
            print(result)
            cons_arr.append(result)
            if result=="Qayam" and pos1=="":
                #print(result,line['Timestamp'])
                pos1=result
                qayam=1
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Qayam" and result=="Ruku":
                #print(result,line['Timestamp'])
                pos1=result
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Ruku" and result=="Qoum" and count2>10:
                #print(result,line['Timestamp'])
                pos1=result
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            # if pos1 == "Qayam" and result == "Sajda" and count2 > 8:
            #     print(result, line['Timestamp'])
            #     pos1 = result
            #     count2 = 0
            #     count1=1
            if pos1=="Qayam" and result=="Qoum" and count2>10:
                #print(result,line['Timestamp'])
                pos1=result
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Qoum" and result=="Sajda" and count1==0 and count2>10:
                #print(result,line['Timestamp'])
                pos1=result
                count1=1
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Sajda" and result=="Jalsa" and count1==1:
                #print(result,line['Timestamp'])
                pos1=result
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Jalsa" and result=="Sajda" and count1==1 and count2>10:
                count1=2
                #print(result,line['Timestamp'])
                pos1=result
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Sajda" and result=="Sajda" and count1==1 and count2>10:
                #print(result,line['Timestamp'])
                pos1=result
                count1=2
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Sajda" and result=="Tashahud" and qayam==2 and count2>10:
                count1=0
                #print(result,line['Timestamp'])
                pos1=result
                qayam=0
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Sajda" and result=="Qayam" and qayam==1 and count2>10 and count1==2:
                #print(result,line['Timestamp'])
                pos1=result
                qayam=2
                count1=0
                count2 = 0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            if pos1=="Tashahud" and result=="Qayam" and count2>10:
                #print(result,line['Timestamp'])
                pos1=result
                qayam=1
                count2=0
                newpos = {
                    i: {'Timestamp': line['Timestamp'], 'Posture': result, 'Rakah': line['Rakah'], 'Name': line['Name'],
                        'Type': line['Type']
                        }}
                pos.update(newpos)
            count2+=1
            i+=1
    qayam_count=0
    incr=1
    with open('file4.csv', 'w') as f:
        writer = csv.writer(f)
        row = ['Time', 'Posture', 'TotalRakah', 'Name', 'type']
        writer.writerow(row)
        for item, value in pos.items():
            print(item, ":", value)
            arr=[]
            for itr, val in value.items():
                #print(itr,":",val)
                if val=="Qayam":
                    qayam_count+=1
                arr.append(val)
            print(arr)
            row=[]
            for a in arr:
                row.append(a)
            if qayam_count == 5 and rakah_count=="4":
                break
            if qayam_count==3 and rakah_count=="2":
                break
            if qayam_count==4 and rakah_count=="3":
                break
            writer.writerow(row)
            dic1 = {
                incr: {'Name': row[3], 'Posture': row[1], 'Rakah': row[2], 'Timestamp': row[0],
                       'Type': row[4]
                       }}
            dic2.update(dic1)
            incr += 1
    # size of the list
    size = len(cons_arr)

    # looping till length - 2
    for i in range(size - 2):

        # checking the conditions
        if cons_arr[i] == cons_arr[i + 1] and cons_arr[i + 1] == cons_arr[i + 2]:
            # printing the element as the
            # conditions are satisfied
            print(cons_arr[i])
    return jsonify(dic2)

@app.route('/reasoner',methods=['GET'])
def reasoner():
    rakahs=2
    pos_order=[]
    with open("file4.csv","r") as f:
        for line in csv.DictReader(f):
            rakahs=line['TotalRakah']
            pos_order.append(line['Posture'])
    reasoner={}
    newstats={}
    t1 = 0
    for i in range(1, 5):
        ifcond1 = 0
        ifcond2 = 0
        print("i: ",i)
        if t1 == len(pos_order):
            break
        if i>int(rakahs):
            if t1 + 6 <= len(pos_order):
                if t1<len(pos_order) and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                        t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[t1 + 6] == "Tashahud":
                    newstats = {i: {"Rakah": i, "Status": "Error", 'PostureMiss': "None", 'PostureExtra': "None",
                                    "RakahExtra": "Yes"}}
                    reasoner.update(newstats)

            break
        if i == 1 or i == 3:
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                    t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[t1 + 6] == "Qayam":
                t1+=6
                newstats = {i:{"Rakah":i,"Status":"tic",'PostureMiss':"None",'PostureExtra':"None","RakahExtra":"None"}}
                reasoner.update(newstats)
                ifcond1 = 1
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Qoum" and pos_order[t1 + 2] == "Sajda" and pos_order[
                        t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Qayam":
                newstats = {i: {"Rakah": i, "Status": "Wrong", 'PostureMiss': "Ruku,Jalsa", 'PostureExtra': "None",
                                    "RakahExtra": "None"}}
                reasoner.update(newstats)
                t1+=4
                ifcond1 = 1
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                    t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[t1 + 6] == "Jalsa" and pos_order[t1 + 7]=="Sajda":
                newstats = {i:{"Rakah":i,"Status":"Error",'PostureMiss':"None",'PostureExtra':"Jalsa,Sajda","RakahExtra":"None"}}
                reasoner.update(newstats)
                t1+=8
                ifcond1 = 1

            if t1 + 6 <= len(pos_order):
                if ifcond1==0 and int(rakahs) == 3 and i == 3 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[
                    t1 + 2] == "Qoum" and pos_order[t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[t1 + 6] == "Tashahud":
                    newstats = {i:{"Rakah": i, "Status": "tic",'PostureMiss':"None",'PostureExtra':"None","RakahExtra":"None"}}
                    reasoner.update(newstats)
                    t1+=7
                    ifcond1 = 1

            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Qoum" and pos_order[t1 + 2] == "Sajda" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Qayam":
                print("insdeeeee")
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Ruku",'PostureExtra':"None","RakahExtra":"None"}}
                reasoner.update(newstats)
                print("newstats: ",newstats)
                print("reasoner: ",newstats)
                t1+=5
                ifcond1 = 1
                print(pos_order[t1])
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Qayam":
                newstats = {i: {"Rakah": i, "Status": "Wrong", 'PostureMiss': "Jalsa,Sajda2", 'PostureExtra': "None",
                                "RakahExtra": "None"}}
                reasoner.update(newstats)
                t1+=4
                ifcond1 = 1
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Qoum" and pos_order[t1 + 2] == "Sajda" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1+5]=="Sajda" and pos_order[t1+6]=="Tashahud":
                print("insideee extraaaa")
                newstats = {i:{"Rakah": i, "Status": "Error", 'PostureMiss': "None",'PostureExtra':"Sajda","RakahExtra":"None"}}
                reasoner.update(newstats)
                t1+=7
                ifcond1 = 1
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Qayam":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda2", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                t1+=5
                ifcond1 = 1
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Sajda" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Qoum", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                t1+=5
                ifcond1 = 1
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda1", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                t1+=5
                ifcond1 = 1
            if ifcond1==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Sajda":
                newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Jalsa", 'PostureExtra': "None","RakahExtra":"None"}}
                reasoner.update(newstats)
                t1+=5
                ifcond1 = 1
        if i == 2 or i == 4:
            if t1 == len(pos_order):
                break
            if t1+6 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[
                t1 + 6] == "Tashahud":
                    newstats = {i:{"Rakah": i, "Status": "tic", 'PostureMiss': "None", 'PostureExtra': "None","RakahExtra":"None"}}
                    reasoner.update(newstats)
                    t1+=7
                    ifcond2 = 1
            if t1+5 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Qoum" and pos_order[t1 + 2] == "Sajda" and pos_order[
                        t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Tashahud":
                    newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Ruku", 'PostureExtra': "None","RakahExtra":"None"}}
                    reasoner.update(newstats)
                    t1+=6
                    ifcond2 = 1

            if t1 + 4 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Qoum" and pos_order[t1 + 2] == "Sajda" and pos_order[
                                t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Tashahud":
                    newstats = {i: {"Rakah": i, "Status": "Wrong", 'PostureMiss': "Ruku,Jalsa", 'PostureExtra': "None",
                                        "RakahExtra": "None"}}
                    reasoner.update(newstats)
                    t1 += 5
                    ifcond2 = 1
            if t1 + 7 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                    t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[
                        t1 + 6] == "Jalsa" and pos_order[t1 + 7] == "Sajda":
                    newstats = {i: {"Rakah": i, "Status": "Error", 'PostureMiss': "None", 'PostureExtra': "Sajda",
                                    "RakahExtra": "None"}}
                    reasoner.update(newstats)
                    t1 += 8
                    ifcond2 = 1
            if t1 + 4 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                        t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Tashahud":
                    newstats = {i: {"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda2", 'PostureExtra': "None",
                                    "RakahExtra": "None"}}
                    t1 += 5
                    reasoner.update(newstats)
                    enomali=1
                    ifcond2 = 1
            if t1 + 5 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Sajda" and pos_order[
                        t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Tashahud":
                    newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Qoum", 'PostureExtra': "None","RakahExtra":"None"}}
                    t1 += 6
                    reasoner.update(newstats)
                    ifcond2 = 1
            if t1 + 5 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                        t1 + 3] == "Jalsa" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Tashahud":
                    newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda1", 'PostureExtra': "None","RakahExtra":"None"}}
                    reasoner.update(newstats)
                    t1 += 6
                    ifcond2 = 1
            if t1 + 5 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                        t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Sajda" and pos_order[t1 + 5] == "Tashahud":
                    newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Jalsa", 'PostureExtra': "None","RakahExtra":"None"}}
                    reasoner.update(newstats)
                    t1 += 6
                    ifcond2 = 1
            if t1 + 5 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                        t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Tashahud":
                    newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Sajda2", 'PostureExtra': "None","RakahExtra":"None"}}
                    reasoner.update(newstats)
                    t1 += 6
                    ifcond2 = 1
            if t1 + 6 <= len(pos_order):
                if ifcond2==0 and pos_order[t1] == "Qayam" and pos_order[t1 + 1] == "Ruku" and pos_order[t1 + 2] == "Qoum" and pos_order[
                    t1 + 3] == "Sajda" and pos_order[t1 + 4] == "Jalsa" and pos_order[t1 + 5] == "Sajda" and pos_order[
                        t1 + 6] == "Qayam":
                    newstats = {i:{"Rakah": i, "Status": "Wrong", 'PostureMiss': "Tashahud", 'PostureExtra': "None","RakahExtra":"None"}}
                    reasoner.update(newstats)
                    t1 += 6
                    ifcond2 = 1

    return jsonify(reasoner)

if __name__ == '__main__':
    app.run(debug=True)

#https://tizenflask.herokuapp.com/getValues