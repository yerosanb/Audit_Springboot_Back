package com.afr.fms.Common.Recommendation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

	@Autowired
	private RecommendationMapper recommendationMapper;

	public void createRecommendation(Recommendation recommendation) {
		recommendationMapper.createRecommendation(recommendation);

	}

	public void updateRecommendation(Recommendation recommendation) {
		recommendationMapper.updateRecommendation(recommendation);
	}

	public List<Recommendation> getRecommendation(Long user_id) {
		return recommendationMapper.getRecommendation(user_id);
	}

	public List<Recommendation> getRecommendations() {
		return recommendationMapper.getRecommendations();
	}

	public List<Recommendation> getRecommendationsByAuditType(String audit_type) {
		return recommendationMapper.getRecommendationsByAuditType(audit_type);
	}

	public void deleteRecommendation(Long id) {
		recommendationMapper.deleteRecommendation(id);
	}

}
