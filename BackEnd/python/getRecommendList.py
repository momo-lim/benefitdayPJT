import numpy as np
import sys
import pymysql
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity

service_id = int(sys.argv[1])
input_tags = sys.argv[2]
location = sys.argv[3]

connection = pymysql.connect(
    user='ssafy',
    passwd='ssafy',
    host='52.79.231.137',
    db='profitday',
    charset='utf8',
    cursorclass=pymysql.cursors.DictCursor
)
cursor = connection.cursor()
cursor.execute("select * from service_tag st;")
service_tag = cursor.fetchall()
df = pd.DataFrame(service_tag)
keys = np.unique(df['service_id'].values.tolist())
tagDF = pd.DataFrame(index=range(0, 0), columns={'service_id', 'tags'})
cursor.execute("select service_id,도_특별시_광역시,시_군_구 from service")
service = cursor.fetchall()
serviceDF = pd.DataFrame(service, index=range(0, len(service)))
cursor.execute("select 도_특별시_광역시 from service group by 도_특별시_광역시 having 도_특별시_광역시 not like '%도' and 도_특별시_광역시 not like '%시';")
central = cursor.fetchall()
centralDF = pd.DataFrame(central, index=range(0, len(central)))
do = ""
si = ""
if location != "":
    do = location.split(" ")[0]
    si = location.split(" ")[1]

for i in keys:
    serviceDo = serviceDF[serviceDF['service_id'] == i]['도_특별시_광역시'].values
    serviceSi = serviceDF[serviceDF['service_id'] == i]['시_군_구'].values
    if serviceDo == do or (centralDF['도_특별시_광역시'] == serviceDo[0]).any():
        if serviceSi == "" or serviceSi == si:
            tagList = df[df['service_id'] == i]['tag_id'].values.tolist()
            tagList.sort()
            tags = " ".join(tagList)
            data = {'service_id': [i], 'tags': [tags]}
            tempDF = pd.DataFrame(data)
            tagDF = pd.concat([tagDF, tempDF], ignore_index=True)

if service_id == -1:
    tagDF = pd.concat([tagDF, pd.DataFrame({'service_id': [service_id], 'tags': [input_tags]})], ignore_index=True)
count_vector = CountVectorizer(ngram_range=(1, 1))
c_vector_tag = count_vector.fit_transform(tagDF['tags'])
tag_c_sim = cosine_similarity(c_vector_tag, c_vector_tag).argsort()[:, ::-1]

target_service_index = tagDF[tagDF['service_id'] == service_id].index.values
sim_index = tag_c_sim[target_service_index].reshape(-1)
sim_index = sim_index[sim_index != target_service_index]
result = tagDF.iloc[sim_index]
print(result.to_json(orient='records'))
