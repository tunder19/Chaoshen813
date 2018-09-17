package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.List;

public class M_VoteBean implements Serializable{

    /**
     * head : {"msg":"操作成功","code":1}
     * data : [{"total":38,"seasons":[{"year":2018,"leagueId":1,"season":"英超 18/19"}],"round":5,"teams":[{"id":264,"leagueId":1,"year":2018,"name":"阿森纳","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h7uI.png"},{"id":263,"leagueId":1,"year":2018,"name":"西汉姆联","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h84K.png"},{"id":262,"leagueId":1,"year":2018,"name":"莱切斯特城","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h8H2.png"},{"id":261,"leagueId":1,"year":2018,"name":"纽卡斯尔联","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h8TS.png"},{"id":260,"leagueId":1,"year":2018,"name":"狼队","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h98G.jpg"},{"id":259,"leagueId":1,"year":2018,"name":"沃特福德","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hHX0.jpg"},{"id":258,"leagueId":1,"year":2018,"name":"水晶宫","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hJdN.png"},{"id":257,"leagueId":1,"year":2018,"name":"曼彻斯特联","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hJqB.png"},{"id":256,"leagueId":1,"year":2018,"name":"曼彻斯特城","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hK2F.png"},{"id":255,"leagueId":1,"year":2018,"name":"热刺","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hK76.png"},{"id":254,"leagueId":1,"year":2018,"name":"布莱顿","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hKBT.jpg"},{"id":253,"leagueId":1,"year":2018,"name":"富勒姆","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hKS9.jpg"},{"id":252,"leagueId":1,"year":2018,"name":"埃弗顿","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hKmq.png"},{"id":251,"leagueId":1,"year":2018,"name":"哈德斯菲尔德","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hKtF.jpg"},{"id":250,"leagueId":1,"year":2018,"name":"南安普敦","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLAH.png"},{"id":249,"leagueId":1,"year":2018,"name":"加的夫城","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLGX.jpg"},{"id":248,"leagueId":1,"year":2018,"name":"利物浦","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLZR.png"},{"id":247,"leagueId":1,"year":2018,"name":"切尔西","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLbC.png"},{"id":246,"leagueId":1,"year":2018,"name":"伯恩茅斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLgV.jpg"},{"id":245,"leagueId":1,"year":2018,"name":"伯恩利","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hM7s.png"}],"name":"英超","logo":"https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/201103030955300.jpg","updateTime":"2018-09-12 16:33:23","id":1},{"total":38,"seasons":[{"year":2018,"leagueId":2,"season":"意甲 18/19"}],"round":4,"teams":[{"id":244,"leagueId":2,"year":2018,"name":"都灵","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hMQM.png"},{"id":243,"leagueId":2,"year":2018,"name":"那不勒斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hMUJ.png"},{"id":242,"leagueId":2,"year":2018,"name":"萨索洛","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hMW3.png"},{"id":241,"leagueId":2,"year":2018,"name":"罗马","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hMZ8.png"},{"id":240,"leagueId":2,"year":2018,"name":"热那亚","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hMcW.png"},{"id":239,"leagueId":2,"year":2018,"name":"桑普多利亚","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hMk5.png"},{"id":238,"leagueId":2,"year":2018,"name":"斯帕尔","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hMoA.jpg"},{"id":237,"leagueId":2,"year":2018,"name":"拉齐奥","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hN6d.png"},{"id":236,"leagueId":2,"year":2018,"name":"恩波利","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hN9C.png"},{"id":235,"leagueId":2,"year":2018,"name":"弗洛辛诺内","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hNCX.jpg"},{"id":234,"leagueId":2,"year":2018,"name":"帕尔马","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hNZT.png"},{"id":233,"leagueId":2,"year":2018,"name":"尤文图斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hNdG.png"},{"id":232,"leagueId":2,"year":2018,"name":"国际米兰","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hNgf.png"},{"id":231,"leagueId":2,"year":2018,"name":"卡利亚里","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hNjr.png"},{"id":230,"leagueId":2,"year":2018,"name":"博洛尼亚","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hNo2.jpg"},{"id":229,"leagueId":2,"year":2018,"name":"切沃","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hOHi.png"},{"id":228,"leagueId":2,"year":2018,"name":"佛罗伦萨","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hOL1.png"},{"id":227,"leagueId":2,"year":2018,"name":"亚特兰大","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hONV.png"},{"id":226,"leagueId":2,"year":2018,"name":"乌迪内斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hOPD.png"},{"id":225,"leagueId":2,"year":2018,"name":"AC米兰","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hOS3.png"}],"name":"意甲","logo":"https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/yijia.jpg","updateTime":"2018-09-12 16:33:22","id":2},{"total":38,"seasons":[{"year":2018,"leagueId":3,"season":"西甲 18/19"}],"round":4,"teams":[{"id":224,"leagueId":3,"year":2018,"name":"马德里竞技","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hOmm.png"},{"id":223,"leagueId":3,"year":2018,"name":"韦斯卡","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hOuF.jpg"},{"id":222,"leagueId":3,"year":2018,"name":"雷加利斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hPDL.jpg"},{"id":221,"leagueId":3,"year":2018,"name":"阿拉维斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hPXj.jpg"},{"id":220,"leagueId":3,"year":2018,"name":"赫罗纳","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hPts.jpg"},{"id":219,"leagueId":3,"year":2018,"name":"赫塔费","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQEG.png"},{"id":218,"leagueId":3,"year":2018,"name":"西班牙人","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQJ6.png"},{"id":217,"leagueId":3,"year":2018,"name":"莱万特","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQM8.png"},{"id":216,"leagueId":3,"year":2018,"name":"维戈塞尔塔","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQPl.png"},{"id":215,"leagueId":3,"year":2018,"name":"皇家马德里","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQTu.png"},{"id":214,"leagueId":3,"year":2018,"name":"皇家贝蒂斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQX2.jpg"},{"id":213,"leagueId":3,"year":2018,"name":"皇家社会","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQnw.png"},{"id":212,"leagueId":3,"year":2018,"name":"毕尔巴鄂竞技","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQrp.png"},{"id":211,"leagueId":3,"year":2018,"name":"比利亚雷亚尔","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQuB.png"},{"id":210,"leagueId":3,"year":2018,"name":"巴塞罗那","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hQy4.png"},{"id":209,"leagueId":3,"year":2018,"name":"巴利亚多利德","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hR22.jpg"},{"id":208,"leagueId":3,"year":2018,"name":"巴列卡诺","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hRsf.png"},{"id":207,"leagueId":3,"year":2018,"name":"巴伦西亚","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hRyh.png"},{"id":206,"leagueId":3,"year":2018,"name":"塞维利亚","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hS0p.png"},{"id":205,"leagueId":3,"year":2018,"name":"埃瓦尔","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hS3z.png"}],"name":"西甲","logo":"https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/200506141515440.jpg","updateTime":"2018-09-12 16:33:21","id":3},{"total":34,"seasons":[{"year":2018,"leagueId":4,"season":"德甲 18/19"}],"round":3,"teams":[{"id":204,"leagueId":4,"year":2018,"name":"霍芬海姆","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hSlj.png"},{"id":203,"leagueId":4,"year":2018,"name":"门兴格拉德巴赫","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hSur.png"},{"id":202,"leagueId":4,"year":2018,"name":"莱比锡红牛","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hT6Z.jpg"},{"id":201,"leagueId":4,"year":2018,"name":"美因茨","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hVle.png"},{"id":200,"leagueId":4,"year":2018,"name":"纽伦堡","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hVqZ.jpg"},{"id":199,"leagueId":4,"year":2018,"name":"法兰克福","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hW6a.png"},{"id":198,"leagueId":4,"year":2018,"name":"沙尔克04","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hW8z.png"},{"id":197,"leagueId":4,"year":2018,"name":"沃尔夫斯堡","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWBN.png"},{"id":196,"leagueId":4,"year":2018,"name":"汉诺威96","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWDZ.png"},{"id":195,"leagueId":4,"year":2018,"name":"柏林赫塔","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWFz.png"},{"id":194,"leagueId":4,"year":2018,"name":"杜塞尔多夫","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWJz.gif"},{"id":193,"leagueId":4,"year":2018,"name":"斯图加特","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWe4.png"},{"id":192,"leagueId":4,"year":2018,"name":"拜仁慕尼黑","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWfb.png"},{"id":191,"leagueId":4,"year":2018,"name":"弗赖堡","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWiS.png"},{"id":190,"leagueId":4,"year":2018,"name":"奥格斯堡","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWky.png"},{"id":189,"leagueId":4,"year":2018,"name":"多特蒙德","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWoj.png"},{"id":188,"leagueId":4,"year":2018,"name":"勒沃库森","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWqL.png"},{"id":187,"leagueId":4,"year":2018,"name":"云达不来梅","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hWuC.png"}],"name":"德甲","logo":"https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/200506141510320.jpg","updateTime":"2018-09-12 16:33:20","id":4},{"total":38,"seasons":[{"year":2018,"leagueId":5,"season":"法甲 18/19"}],"round":5,"teams":[{"id":186,"leagueId":5,"year":2018,"name":"马赛","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hYMo.png"},{"id":185,"leagueId":5,"year":2018,"name":"雷恩","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hYQ5.png"},{"id":184,"leagueId":5,"year":2018,"name":"里昂","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hYTe.png"},{"id":183,"leagueId":5,"year":2018,"name":"里尔","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hYXF.png"},{"id":182,"leagueId":5,"year":2018,"name":"蒙彼利埃","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hYaM.png"},{"id":181,"leagueId":5,"year":2018,"name":"第戎","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hYgs.jpg"},{"id":180,"leagueId":5,"year":2018,"name":"甘冈","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hZ0W.png"},{"id":179,"leagueId":5,"year":2018,"name":"波尔多","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hZ4V.png"},{"id":178,"leagueId":5,"year":2018,"name":"昂热","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hZ7m.jpg"},{"id":177,"leagueId":5,"year":2018,"name":"斯特拉斯堡","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hb4i.png"},{"id":176,"leagueId":5,"year":2018,"name":"摩纳哥","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hbDU.png"},{"id":175,"leagueId":5,"year":2018,"name":"巴黎圣日尔曼","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hbHe.png"},{"id":174,"leagueId":5,"year":2018,"name":"尼斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hbLR.png"},{"id":173,"leagueId":5,"year":2018,"name":"尼姆","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hbRz.jpg"},{"id":172,"leagueId":5,"year":2018,"name":"圣埃蒂安","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hc0Y.png"},{"id":171,"leagueId":5,"year":2018,"name":"图卢兹","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hc3w.png"},{"id":170,"leagueId":5,"year":2018,"name":"卡昂","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hc5q.png"},{"id":169,"leagueId":5,"year":2018,"name":"南特","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hcBC.png"},{"id":168,"leagueId":5,"year":2018,"name":"兰斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hcEI.png"},{"id":167,"leagueId":5,"year":2018,"name":"亚眠","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hcIP.jpg"}],"name":"法甲","logo":"https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/12639.jpg","updateTime":"2018-09-12 16:33:19","id":5},{"total":30,"seasons":[{"year":2018,"leagueId":6,"season":"中超 2018"}],"round":22,"teams":[{"id":166,"leagueId":6,"year":2018,"name":"长春亚泰","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hmkS.jpg"},{"id":165,"leagueId":6,"year":2018,"name":"重庆斯威","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hn50.png"},{"id":164,"leagueId":6,"year":2018,"name":"贵州恒丰","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hn7n.png"},{"id":163,"leagueId":6,"year":2018,"name":"河南建业","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hnBu.jpg"},{"id":162,"leagueId":6,"year":2018,"name":"河北华夏幸福","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hnbJ.png"},{"id":161,"leagueId":6,"year":2018,"name":"江苏苏宁易购","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hnoM.jpg"},{"id":160,"leagueId":6,"year":2018,"name":"广州恒大淘宝","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hoED.gif"},{"id":159,"leagueId":6,"year":2018,"name":"广州富力","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hro1.png"},{"id":158,"leagueId":6,"year":2018,"name":"山东鲁能泰山","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hs44.jpg"},{"id":157,"leagueId":6,"year":2018,"name":"天津泰达","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hsti.png"},{"id":156,"leagueId":6,"year":2018,"name":"天津权健","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01htFt.png"},{"id":155,"leagueId":6,"year":2018,"name":"大连一方","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01htRO.png"},{"id":154,"leagueId":6,"year":2018,"name":"北京人和","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01htwA.jpg"},{"id":153,"leagueId":6,"year":2018,"name":"北京中赫国安","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hx1E.png"},{"id":152,"leagueId":6,"year":2018,"name":"上海绿地申花","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hxDb.jpg"},{"id":151,"leagueId":6,"year":2018,"name":"上海上港","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hy2J.jpg"}],"name":"中超","logo":"https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/200506141450250.jpg","updateTime":"2018-09-13 15:51:16","id":6},{"total":34,"seasons":[{"year":2018,"leagueId":7,"season":"J联赛 2018"}],"round":26,"teams":[{"id":150,"leagueId":7,"year":2018,"name":"鹿岛鹿角","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01i4sE.jpg"},{"id":149,"leagueId":7,"year":2018,"name":"鸟栖沙岩","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01i5BP.jpg"},{"id":148,"leagueId":7,"year":2018,"name":"长崎航海","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01i89Y.jpg"},{"id":147,"leagueId":7,"year":2018,"name":"神户胜利船","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01i8TS.jpg"},{"id":146,"leagueId":7,"year":2018,"name":"磐田喜悦","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01i8qH.jpg"},{"id":145,"leagueId":7,"year":2018,"name":"湘南海洋","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01i9QU.jpg"},{"id":144,"leagueId":7,"year":2018,"name":"清水鼓动","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iAq3.jpg"},{"id":143,"leagueId":7,"year":2018,"name":"浦和红钻","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iB7t.jpg"},{"id":142,"leagueId":7,"year":2018,"name":"横滨水手","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iCE9.jpg"},{"id":141,"leagueId":7,"year":2018,"name":"柏太阳神","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iCr2.jpg"},{"id":140,"leagueId":7,"year":2018,"name":"札幌冈萨多","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iDtX.jpg"},{"id":139,"leagueId":7,"year":2018,"name":"广岛三箭","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iEVX.jpg"},{"id":138,"leagueId":7,"year":2018,"name":"川崎前锋","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iGxz.jpg"},{"id":137,"leagueId":7,"year":2018,"name":"大阪钢巴","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iIu1.jpg"},{"id":136,"leagueId":7,"year":2018,"name":"大阪樱花","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iLf3.jpg"},{"id":135,"leagueId":7,"year":2018,"name":"名古屋鲸八","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iM7n.jpg"},{"id":134,"leagueId":7,"year":2018,"name":"仙台维加泰","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iMYf.jpg"},{"id":133,"leagueId":7,"year":2018,"name":"东京FC","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01iNAt.jpg"}],"name":"J联赛","logo":"https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/200506141453430.jpg","updateTime":"2018-09-13 15:51:15","id":7}]
     */

        /**
         * total : 38
         * seasons : [{"year":2018,"leagueId":1,"season":"英超 18/19"}]
         * round : 5
         * teams : [{"id":264,"leagueId":1,"year":2018,"name":"阿森纳","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h7uI.png"},{"id":263,"leagueId":1,"year":2018,"name":"西汉姆联","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h84K.png"},{"id":262,"leagueId":1,"year":2018,"name":"莱切斯特城","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h8H2.png"},{"id":261,"leagueId":1,"year":2018,"name":"纽卡斯尔联","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h8TS.png"},{"id":260,"leagueId":1,"year":2018,"name":"狼队","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h98G.jpg"},{"id":259,"leagueId":1,"year":2018,"name":"沃特福德","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hHX0.jpg"},{"id":258,"leagueId":1,"year":2018,"name":"水晶宫","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hJdN.png"},{"id":257,"leagueId":1,"year":2018,"name":"曼彻斯特联","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hJqB.png"},{"id":256,"leagueId":1,"year":2018,"name":"曼彻斯特城","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hK2F.png"},{"id":255,"leagueId":1,"year":2018,"name":"热刺","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hK76.png"},{"id":254,"leagueId":1,"year":2018,"name":"布莱顿","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hKBT.jpg"},{"id":253,"leagueId":1,"year":2018,"name":"富勒姆","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hKS9.jpg"},{"id":252,"leagueId":1,"year":2018,"name":"埃弗顿","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hKmq.png"},{"id":251,"leagueId":1,"year":2018,"name":"哈德斯菲尔德","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hKtF.jpg"},{"id":250,"leagueId":1,"year":2018,"name":"南安普敦","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLAH.png"},{"id":249,"leagueId":1,"year":2018,"name":"加的夫城","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLGX.jpg"},{"id":248,"leagueId":1,"year":2018,"name":"利物浦","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLZR.png"},{"id":247,"leagueId":1,"year":2018,"name":"切尔西","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLbC.png"},{"id":246,"leagueId":1,"year":2018,"name":"伯恩茅斯","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hLgV.jpg"},{"id":245,"leagueId":1,"year":2018,"name":"伯恩利","flag":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01hM7s.png"}]
         * name : 英超
         * logo : https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/201103030955300.jpg
         * updateTime : 2018-09-12 16:33:23
         * id : 1
         */

        private int total;
        private int round;
        private String name;
        private String logo;
        private String updateTime;
        private int id;
        private List<SeasonsBean> seasons;
        private List<TeamsBean> teams;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<SeasonsBean> getSeasons() {
            return seasons;
        }

        public void setSeasons(List<SeasonsBean> seasons) {
            this.seasons = seasons;
        }

        public List<TeamsBean> getTeams() {
            return teams;
        }

        public void setTeams(List<TeamsBean> teams) {
            this.teams = teams;
        }

        public static class SeasonsBean implements Serializable {
            /**
             * year : 2018
             * leagueId : 1
             * season : 英超 18/19
             */

            private int year;
            private int leagueId;
            private String season;

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getLeagueId() {
                return leagueId;
            }

            public void setLeagueId(int leagueId) {
                this.leagueId = leagueId;
            }

            public String getSeason() {
                return season;
            }

            public void setSeason(String season) {
                this.season = season;
            }
        }

}
