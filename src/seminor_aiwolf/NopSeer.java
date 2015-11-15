package seminor_aiwolf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.aiwolf.client.base.player.AbstractSeer;
import org.aiwolf.client.lib.TemplateTalkFactory;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Species;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.net.GameInfo;

public class NopSeer extends AbstractSeer {
	
	private List<Agent> distrust_person = new ArrayList<Agent>();
	//自分視点、偽の人

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
	public String talk() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	/**
	 * 引数のAgentのリストからランダムにAgentを選択します
	 * @param agentList
	 * @return
	 */
	private Agent randomSelect(List<Agent> agentList) {
		int num = new Random().nextInt(agentList.size());
		return agentList.get(num);
	}

	@Override
	public Agent vote() {
		List<Agent> whiteAgent = new ArrayList<Agent>(),
				blackAgent = new ArrayList<Agent>();
		
		for(Judge judge: getMyJudgeList()) {
			if (getLatestDayGameInfo().getAliveAgentList().contains(judge.getTarget()))  {
				switch (judge.getResult()) {
				case HUMAN:
					whiteAgent.add(judge.getTarget());
					break;
				case WEREWOLF:
					blackAgent.add(judge.getTarget());
					break;
				}
				
			}
		}
		
		//占いの投票の順番
		// 自分の● → 自分視点偽の人 → 自分の○以外の人
		if (blackAgent.size() > 0) {
			return randomSelect(blackAgent);
		} else if (distrust_person.size() > 0) {
			return randomSelect(distrust_person);
		} else {
			List<Agent> voteCandidates = new ArrayList<Agent>();
			voteCandidates.addAll(getLatestDayGameInfo().getAliveAgentList());
			voteCandidates.remove(getMe());
			voteCandidates.removeAll(whiteAgent);
			return randomSelect(voteCandidates);
		}
	}

}
