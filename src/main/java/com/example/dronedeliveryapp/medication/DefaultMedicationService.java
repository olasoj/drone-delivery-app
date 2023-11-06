package com.example.dronedeliveryapp.medication;

import com.example.dronedeliveryapp.Weight;
import com.example.dronedeliveryapp.medication.model.Medication;
import com.example.dronedeliveryapp.medication.model.MedicationRegistrationRequest;
import com.example.dronedeliveryapp.medication.repository.MedicationRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DefaultMedicationService implements MedicationService {

    static final String NAME_REGEX = "([A-Za-z\\d\\-_]+)";
    static final String CODE_REGEX = "([A-Z\\d\\-_]+)";
    private static final Pattern namePattern;
    private static final Pattern codePattern;
    private static final UrlValidator validator = new UrlValidator();

    static {
        namePattern = Pattern.compile(NAME_REGEX);
        codePattern = Pattern.compile(CODE_REGEX);
    }

    private final MedicationRepository medicationRepository;

    public DefaultMedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    public List<Medication> addMedications(Set<MedicationRegistrationRequest> medicationList) {

        if (medicationList == null || medicationList.isEmpty()) {
            throw new MedicationServiceException("Medications are missing", HttpStatus.BAD_REQUEST);
        }

        Set<Medication> medications = getMedications(medicationList);
        return medicationRepository.saveAll(medications);
    }

    private Set<Medication> getMedications(Set<MedicationRegistrationRequest> medicationList) {

        Set<Medication> medications = new HashSet<>(medicationList.size());

        for (MedicationRegistrationRequest medication : medicationList) {

            if (medication == null) throw new MedicationServiceException("Medication is missing", HttpStatus.BAD_REQUEST);

            Double weight = medication.getWeightInGrams();
            if (weight == null || weight < 0 || weight > 500) throw new MedicationServiceException("Medication is too heavy", HttpStatus.BAD_REQUEST);

            checkMedProps(medication.getName(), "name", namePattern);
            checkMedProps(medication.getCode(), "code", codePattern);

            if (!validator.isValid(medication.getImage())) {
                throw new MedicationServiceException("Medication URI is invalid", HttpStatus.BAD_REQUEST);
            }

            medications.add(
                    new Medication(
                            medication.getName().strip(),
                            new Weight(medication.getWeightInGrams()),
                            medication.getCode().strip(),
                            medication.getImage()
                    )
            );

        }
        return medications;
    }

    private void checkMedProps(String medicationName, String errorMsg, Pattern namePattern) {
        if (medicationName == null || medicationName.isBlank()) {
            throw new MedicationServiceException("Medication " + errorMsg + " is missing", HttpStatus.BAD_REQUEST);
        }

        Matcher matcher = namePattern.matcher(medicationName.strip());

        if (!matcher.matches()) {
            throw new MedicationServiceException("Invalid medication " + errorMsg + " provided", HttpStatus.BAD_REQUEST);
        }
    }


}
