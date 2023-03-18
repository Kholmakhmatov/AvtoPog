package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.LdSvGateAddCreate;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.mapper.MyMapper;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.repository.BranchRepository;
import uz.agrobank.avtopog.repository.LdSvGateAddRepository;
import uz.agrobank.avtopog.repository.LdSvGateRepository;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.response.ResponseDtoList;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kholmakhmatov_A on 3/13/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 3/13/2023
 * @Project : AvtoPog
 **/
@Service
@RequiredArgsConstructor
public class CardService {
    private final BranchRepository branchRepository;
    private final LdSvGateAddRepository ldSvGateAddRepository;
    private final LdSvGateRepository ldSvGateRepository;
    private final MyMapper mapper;

    public List<Branch> getBranches() {
        return branchRepository.getBranchList();
    }

    public ResponseDto<LdSvGateAdd> addCard(LdSvGateAddCreate ldSvGateAddCreate, Long userId) {
        ResponseDto<LdSvGateAdd> responseDto = new ResponseDto<>();
        try {
            boolean existsById = ldSvGateAddRepository.existsById(ldSvGateAddCreate.getId(), ldSvGateAddCreate.getId());
            LdSvGateAdd ldSvGateAdd = new LdSvGateAdd(ldSvGateAddCreate.getId(), ldSvGateAddCreate.getBranch(), ldSvGateAddCreate.getCardNumber(), ldSvGateAddCreate.getExpiryMonth() + ldSvGateAddCreate.getExpiryYear(), userId);
            responseDto.setObj(ldSvGateAdd);
            if (existsById) {
                responseDto.setMessage("Anketa Id avvaldan mavjud");
                responseDto.setObj(ldSvGateAdd);
                return responseDto;
            }
            responseDto.setSuccess(true);
            LdSvGateAdd save = ldSvGateAddRepository.save(ldSvGateAdd);
            responseDto.setMessage("Add new card");
            responseDto.setObj(save);
            return responseDto;
        } catch (Exception e) {
            responseDto.setMessage("Serverda nosozlik adminga murojaat qiling");
            return responseDto;
        }


    }

    public ContentList<LdSvGate> getAllActive(Long id, String brach, String cardNumber, Integer page) {
        ContentList<LdSvGate> contentList = new ContentList<>();
        Integer size = Integer.parseInt(SecretKeys.SIZE);
        if (brach == null || brach.isEmpty()) brach = null;
        if (id == null || id == -1) id = null;
        if (cardNumber == null || cardNumber.length() < 14) cardNumber = null;
        Integer offset = size * page;
        List<LdSvGate> allActive = ldSvGateRepository.findAllActiveUnion(id, brach, cardNumber, id, brach, cardNumber, size, offset);
        contentList.setPage(page);
        List<Integer> allActiveCountList = ldSvGateRepository.findAllActiveCountUnion(id, brach, cardNumber, id, brach, cardNumber);
        contentList.setList(allActive);
        Integer allActiveCount = 0;
        for (Integer integer : allActiveCountList) {
            allActiveCount += integer;
        }
        double div = allActiveCount / 10.0;
        Integer a = (int) Math.ceil(div);
        contentList.setCount(a);
        return contentList;
    }

    public ResponseDto<String> deleteCadById(Long id) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        Optional<LdSvGateAdd> byId = ldSvGateAddRepository.findById(id);
        if (byId.isPresent()) {
            LdSvGateAdd ldSvGateAdd = byId.get();
            ldSvGateAdd.setState(0);
            ldSvGateAddRepository.save(ldSvGateAdd);
            responseDto.setSuccess(true);
            responseDto.setMessage("Card position is passive");
            return responseDto;
        } else {
            Optional<LdSvGate> byIdGate = ldSvGateRepository.findById(id);
            if (byIdGate.isPresent()) {
                LdSvGate ldSvGate = byIdGate.get();
                ldSvGate.setState(0);
                ldSvGateRepository.save(ldSvGate);
                responseDto.setSuccess(true);
                responseDto.setMessage("Card position is passive");
                return responseDto;
            }
        }
        responseDto.setMessage("Card not found");
        return responseDto;
    }


    public LdSvGateAddCreate getCardById(Long id) {
        Optional<LdSvGate> byId = ldSvGateRepository.findById(id);
        if (byId.isPresent()) {
            LdSvGate ldSvGate = byId.get();
            LdSvGateAddCreate ldSvGateAddCreate = mapper.fromLdSvGateToCreate(ldSvGate);
            ldSvGateAddCreate.setExpiryMonth(ldSvGate.getExpiryDate().substring(0, 2));
            ldSvGateAddCreate.setExpiryYear(ldSvGate.getExpiryDate().substring(2, 4));
            return ldSvGateAddCreate;
        } else {
            Optional<LdSvGateAdd> byId1 = ldSvGateAddRepository.findById(id);
            if (byId1.isPresent()) {
                LdSvGateAdd ldSvAdd = byId1.get();
                LdSvGateAddCreate ldSvGateAddCreate = mapper.fromLdSvAddToCreate(ldSvAdd);
                ldSvGateAddCreate.setExpiryMonth(ldSvAdd.getExpiryDate().substring(0, 2));
                ldSvGateAddCreate.setExpiryYear(ldSvAdd.getExpiryDate().substring(2, 4));
                return ldSvGateAddCreate;
            }
        }
        throw new UniversalException("Card not found", HttpStatus.NOT_FOUND);
    }

    public ResponseDto<String> addCardFromFile(MultipartFile[] files, HttpServletResponse response,Long userId) {
        ResponseDto<String>stringResponseDto=new ResponseDto<>();
        List<LdSvGateAddCreate> ldSvGateAddCreateList = excelToList(files,userId);
        List<ResponseDto<LdSvGateAdd>>responseDtoList=new ArrayList<>();
        for (LdSvGateAddCreate ldSvGateAdd : ldSvGateAddCreateList) {
            ResponseDto<LdSvGateAdd> responseDto=addCard(ldSvGateAdd,userId);
            responseDtoList.add(responseDto);
        }
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Cards" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        ExcelGeneratorService generator = new ExcelGeneratorService(responseDtoList);
        try {
            generator.generateExcelFile(response);
        } catch (IOException e) {
            stringResponseDto.setMessage("Server error ");
           return stringResponseDto;
        }
        stringResponseDto.setMessage("Add card");
        stringResponseDto.setSuccess(true);
        return stringResponseDto;

    }

    private List<LdSvGateAddCreate> excelToList(MultipartFile[] files, Long userId) {
        List<LdSvGateAddCreate> ldSvGateAddCreateList=new ArrayList<>();
        MultipartFile file = files[0];
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            throw new UniversalException("Read excel exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {

            XSSFRow row = worksheet.getRow(i);
            Long id=(long) row.getCell(1).getNumericCellValue();
            String branch = String.valueOf((long) row.getCell(0).getNumericCellValue());
            int length = branch.length();
            for (int j = length; j <5 ; j++) {
                branch="0".concat(branch);
            }
            String cardNumber=row.getCell(2).getStringCellValue();
            String expiryDate=row.getCell(3).getStringCellValue();
            LdSvGateAddCreate ldSvGateAdd = new LdSvGateAddCreate(id,branch,cardNumber,expiryDate.substring(0,2),expiryDate.substring(2,4));
            ldSvGateAddCreateList.add(ldSvGateAdd);
        }
        return ldSvGateAddCreateList;
    }
}
