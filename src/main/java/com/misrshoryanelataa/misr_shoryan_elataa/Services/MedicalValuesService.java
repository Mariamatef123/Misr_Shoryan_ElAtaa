package com.misrshoryanelataa.misr_shoryan_elataa.Services;

import com.misrshoryanelataa.misr_shoryan_elataa.DTO.ValidationResponse;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.MedicalValuesEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.MedicalValuesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MedicalValuesService {
    @Autowired
    private MedicalValuesRepo medRepo;

    private boolean shouldShow(MedicalValuesEntity q, Map<Integer, String> answers) {

        if (q.getDependsOnQuestionId() == null)
            return true;

        String parentAnswer = answers.get(q.getDependsOnQuestionId());

        return parentAnswer != null &&
                parentAnswer.equalsIgnoreCase("Yes");
    }

    public ValidationResponse validate(Map<Integer, String> answers) {

        List<MedicalValuesEntity> questions =
                medRepo.findAll(Sort.by("id"));

        for (MedicalValuesEntity q : questions) {

            if (!shouldShow(q, answers)) {
                continue;
            }

            String answer = answers.get(q.getId());
            if (answer == null) {
                return new ValidationResponse(false,
                        "Missing answer for: " + q.getTitle());
            }

            switch (q.getType()) {

                case NUMBER:

                    try {
                        double value = Double.parseDouble(answer);

                        if (q.getMinValue() != null && value < q.getMinValue()) {
                            return new ValidationResponse(false,
                                    "You are not eligible because you didn't match the donation criteria");
                        }

                        if (q.getMaxValue() != null && value > q.getMaxValue()) {
                            return new ValidationResponse(false,
                                    "You are not eligible because you didn't match the donation criteria");
                        }

                    } catch (NumberFormatException e) {
                        return new ValidationResponse(false,
                                q.getTitle() + " must be a number");
                    }

                    break;

                case YES_NO:


                    if (!answer.equalsIgnoreCase("Yes") &&
                            !answer.equalsIgnoreCase("No")) {

                        return new ValidationResponse(false,
                                q.getTitle() + " must be Yes or No");
                    }
                    if (answer.equalsIgnoreCase("Yes") &&
                            Boolean.TRUE.equals(q.getYesIsInvalid())) {

                        return new ValidationResponse(false,
                                "You are not eligible because you didn't match the donation criteria");
                    }

                    break;
            }
        }

        return new ValidationResponse(true,
                "You are eligible to donate");
    }
    public List<String> getAllQuestionTitles() {

        return medRepo.findAll(Sort.by("id"))
                .stream()
                .map(MedicalValuesEntity::getTitle)
                .toList();
    }
}
