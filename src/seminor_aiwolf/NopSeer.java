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
	static final int CO_DAY = 3; // 最低でもCOする日
	boolean isComingOut = false; // カミングアウトをしたかどうか
	boolean todayCOTimingJudged = false; // 今日その日にCOするかを判定したかどうか
	int readTalkNum = 0;

	//報告済みのJudgeを格納
	List<Judge> myToldJudgeList = new ArrayList<Judge>();
	//偽占いCOしているプレイヤーのリスト
	List<Agent> fakeSeerCOAgent = new ArrayList<Agent>();

	@Override
	public Agent divine() {
		//占い対象の候補者リスト
		List<Agent> divineCandidates = new ArrayList<Agent>();

		//生きているプレイヤーを候補者リストに加える
		divineCandidates.addAll(getLatestDayGameInfo().getAliveAgentList());
		//自分自身と既に占ったことのあるプレイヤーは候補から外す
		divineCandidates.remove(getMe());
		for(Judge judge: getMyJudgeList()){
			if(divineCandidates.contains(judge.getTarget())){
				divineCandidates.remove(judge.getTarget());
			}
		}
		if(divineCandidates.size() > 0){
			//候補者リストからランダムに選択
			return randomSelect(divineCandidates);
		}else{
			//候補者がいない場合は自分を占う
			return getMe();
		}
	}
	
	public Agent randomSelect(List<Agent> agentList){
		int num = new Random().nextInt(agentList.size());
		return agentList.get(num);
	}

	@Override
	public void finish() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void update(GameInfo gameInfo) {
		super.update(gameInfo);
		// 今日のログを取得
		List<Talk> talkList = gameInfo.getTalkList();

		for (int i = readTalkNum; i < talkList.size(); i++) {
			Talk talk = talkList.get(i);
			// 発話をパース
			Utterance utterance = new Utterance(talk.getContent());

			// 発話のトピックごとに処理
			switch (utterance.getTopic()) {
			case COMINGOUT:
				// カミングアウトの発話の処理
				// 自分以外で占いCOしているプレイヤーの場合
				if (utterance.getRole() == Role.SEER
						&& !talk.getAgent().equals(getMe())) {
					fakeSeerCOAgent.add(utterance.getTarget());
				}
				break;
			case DIVINED:
				// 占い結果の発話の処理
				break;
			default:
				break;
			}
			readTalkNum++;
		}
	}

	@Override
	public String talk() {
		if(isComingOut){
			//占い結果順次報告
			for(Judge judge: getMyJudgeList()){
				if(!myToldJudgeList.contains(judge)){
					String resultTalk = TemplateTalkFactory.divined(judge.getTarget(), judge.getResult());
					myToldJudgeList.add(judge);
					return resultTalk;
				}
			}
		}else{
			for(Judge judge: getMyJudgeList()){
				if(judge.getResult() == Species.WEREWOLF){//黒発見したら
					String comingoutTalk = TemplateTalkFactory.comingout(getMe(), getMyRole());
					isComingOut = true;
					return comingoutTalk;
				}else if(!fakeSeerCOAgent.isEmpty()){//偽占いCOがあったら
					String comingoutTalk = TemplateTalkFactory.comingout(getMe(), getMyRole());
					isComingOut = true;
					return comingoutTalk;
				}else if(getLatestDayGameInfo().getDay() >= 3){//3日目になったら
					String comingoutTalk = TemplateTalkFactory.comingout(getMe(), getMyRole());
					isComingOut = true;
					return comingoutTalk;
				}
			}
		}
		return Talk.OVER;
	}

	@Override
	public Agent vote() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
