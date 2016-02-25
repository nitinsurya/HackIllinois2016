# HeartCop #HackIllinois
Critical medical alerts, visualization and data analysis

Project page: http://devpost.com/software/critical-medical-alerts-visualization-data-analysis

# Description

This project aims at saving the lives of patients who are in need of critical medical attention. In specific, we have been working on Atrial fibrillation which is a root cause of heart attack. A wireless detection (heart attack may occur within 0.5 hours of atrial fibrillation symptoms) and transmission via wifi to the server can help to send alert messages to the ambulance and also help the Doctor by sending vital ECG parameters to visualize the signal. Our work focusses on - a. Transmitting the obtained sensor values on the patient side via wifi to the server. b. Devised an algorithm (Change in R-R Interval) to detect atrial fibrillation. Step 1: Detection of R waves and marking of R peaks. Step 2: Calculation of RRI (the duration of adjoined R peaks). Step 3: Calculation of the variation of consecutive RRI (del(RRI)). Step 4: Activation of the alarm system when del(RRI)>150 ms occurs. c. Based on the results of the algorithm, configured an alert/trigger to ambulance when abnormal conditions occur. d. Send alert message to the ambulance. e. Transfer vital parameters to the Doctor's end. f. Visualize the ECG signal (detected abnormal) along with the key parameters on the Doctors's mobile/PDA. g. Use IMO API at the server end to mine past data to derive location specific (disease)syndromes.

DEMO Capability: A working prototype

Can be extended to other critical diagnosis with prediction on required medication using APIs from IMO too.

Sponsor Category apart from HackIllinois consideration : IMO


# CONTRIBUTORS

Karthik(Karthi/Lead Architect)

Arun(Hip-Hop dancer/Android warrior)

Heeba(Zumba instructor/Data Science Rockstar)

Gautham(Jingles/Embedded master)

Myself :P
