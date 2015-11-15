package org.aiwolf.Agentone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aiwolf.client.base.player.AbstractSeer;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Vote;
import org.aiwolf.common.net.GameInfo;


public class Seerone extends AbstractSeer {

	public Seerone(MyRoleAssignPlayer mrap) {
		super();

	}



	public int votenum[] = new int[16];
	Map<Integer, List<Vote>> votelist = new HashMap<Integer, List<Vote>>();//マップで投票結果を格納
	public int nowday = 0;
	public GameInfo gInfo;//その日のgameInfoを格納
	public int i, x, y;
	public List<Agent> voteagent = new ArrayList<Agent>();//最多投票に入れたエージェントのリスト



	@Override
	public Agent divine() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void finish() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(GameInfo gameInfo) {//
	gInfo = gameInfo;
	}

	@Override
	public void dayStart(){
		nowday = gInfo.getDay();
		x = 0;
		y = 0;
		voteagent.clear();
		if(votelist.get(nowday - 1) == null){//今日の日付の前の日の投票結果はまだ入ってないだろうので
			votelist.put(nowday - 1, new ArrayList<Vote>());//新しい日付の完成
		}
if(nowday >= 2){
		votelist.get(nowday - 1).addAll(gInfo.getVoteList());//投票結果を追加

for(i = 0; i < votelist.get(nowday -1).size();i++){
	votenum[votelist.get(nowday - 1).get(i).getTarget().getAgentIdx()]++;
}
for(i = 1;i < 16;i++){
	if(votenum[i] > x){
		x = votenum[i];
		y = i;
	}
}

for(i = 0; i < votelist.get(nowday - 1).size();i++){
if(votelist.get(nowday - 1).get(i).getTarget().getAgentIdx() == y){
	voteagent.add(votelist.get(nowday - 1).get(i).getAgent());//ここでvoteagentに最多投票されたエージェントに投票したエージェントが格納
}
}
//for(i = 0; i < voteagent.size();i++){
//System.out.printf("%s\n",voteagent.get(i));
//}
}


	}

	@Override
	public String talk() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Agent vote() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
