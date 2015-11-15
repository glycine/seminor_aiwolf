package seminor_aiwolf;

import java.util.ArrayList;
import java.util.List;

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

	// 偽占いCOしているプレイヤーのリスト
	List<Agent> fakeSeerCOAgent = new ArrayList<Agent>();

	@Override
	public Agent divine() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
		GameInfo gameInfo = this.getLatestDayGameInfo();
		if (isComingOut) {
			isComingOut = true;
		} else {
			for (Judge judge : getMyJudgeList()) {
				if (judge.getResult() == Species.WEREWOLF) {
					String comingoutTalk = TemplateTalkFactory.comingout(
							getMe(), getMyRole());
					return comingoutTalk;
				} else if (false) {
					String comingoutTalk = TemplateTalkFactory.comingout(
							getMe(), getMyRole());
					return comingoutTalk;
				} else if (getLatestDayGameInfo().getDay() >= 3) {
					String comingoutTalk = TemplateTalkFactory.comingout(
							getMe(), getMyRole());
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
