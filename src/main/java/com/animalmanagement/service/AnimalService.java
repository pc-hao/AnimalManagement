package com.animalmanagement.service;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.config.ImageConfig;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class AnimalService {
    // 在服务器上记得一下这个，还有python文件也是
    public static String LOCAL = "local";
    public static String SERVER = "server";
    private static String runMode = LOCAL;
    private static HashMap<Integer, String> predictId2Name = new HashMap<>();
    // todo 此处后续最好根据数据库来，不方便的话再说
    // todo 这个最后实际使用的时候要根据动协那边的图片来
    static {
        predictId2Name.put(1, "馆长");
        predictId2Name.put(2, "小2");
        predictId2Name.put(3, "小3");
        predictId2Name.put(4, "小4");
        predictId2Name.put(5, "小5");
        predictId2Name.put(6, "小6");
        predictId2Name.put(7, "小7");
        predictId2Name.put(8, "小8");
        predictId2Name.put(9, "小9");
        predictId2Name.put(10, "小10");
        predictId2Name.put(11, "小11");
        predictId2Name.put(12, "小12");
        predictId2Name.put(13, "小13");
        predictId2Name.put(14, "小14");
        predictId2Name.put(15, "小15");
        predictId2Name.put(16, "小16");
        predictId2Name.put(17, "小17");
        predictId2Name.put(18, "小18");
        predictId2Name.put(19, "小19");
        predictId2Name.put(20, "小20");
    }

    private final static Integer PAGE_SIZE = 10;

    private static final String PICTURE_SAVE_PATH_FRONT = ImageConfig.frontPath + "/animal/";

    private final static String PICTURE_SAVE_PATH = ImageConfig.savePath + "/animal/";

    private static final String DEFAULT_IMAGE_PATH = ImageConfig.frontPath + "/animal/default.png";

    @Autowired
    AnimalMapper animalMapper;

    public Map<String, Object> adminAnimalGet(AdminAnimalGetBo adminAnimalGetBo) {
        AnimalExample example = new AnimalExample();

        example.createCriteria().andNameLike("%" + adminAnimalGetBo.getContext() + "%");

        List<Animal> animalList = animalMapper.selectByExample(example);

        animalList.sort(Comparator.comparing(Animal::getName));

        List<AdminAnimalGetVo> voList = animalList
                .stream()
                .map(e -> {
                    AdminAnimalGetVo vo = new AdminAnimalGetVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = adminAnimalGetBo.getPage() * adminAnimalGetBo.getPageNum();
        if (start >= animalList.size()) {
            map.put("records", null);
        } else {
            int end = Math.min(start + adminAnimalGetBo.getPageNum(), animalList.size());
            map.put("records", animalList.subList(start, end));
        }
        return map;
    }

    public AdminAnimalContentVo adminAnimalContent(AdminAnimalContentBo adminAnimalContentBo) {
        Animal animal = animalMapper.selectByPrimaryKey(adminAnimalContentBo.getRecordId());
        if (Objects.isNull(animal)) {
            throw new RuntimeException("Animal ID Does Not Exist");
        }

        AdminAnimalContentVo vo = new AdminAnimalContentVo();
        BeanUtils.copyProperties(animal, vo);

        return vo;
    }

    public void adminAnimalModify(AdminAnimalModifyBo adminAnimalModifyBo) {
        Animal animal = animalMapper.selectByPrimaryKey(adminAnimalModifyBo.getRecordId());
        if (Objects.isNull(animal)) {
            throw new RuntimeException("Animal ID Does Not Exist");
        }

        if (!Objects.isNull(adminAnimalModifyBo.getAdopted()) && !adminAnimalModifyBo.getName().isEmpty()) {
            animal.setName(adminAnimalModifyBo.getName());
        }
        if (!Objects.isNull(adminAnimalModifyBo.getAdopted()) && !adminAnimalModifyBo.getIntro().isEmpty()) {
            animal.setIntro(adminAnimalModifyBo.getIntro());
        }
        if (!Objects.isNull(adminAnimalModifyBo.getAdopted())) {
            animal.setAdopted(adminAnimalModifyBo.getAdopted());
        }
        if (!Objects.isNull(adminAnimalModifyBo.getAvatar())) {
            String newAvatar = PICTURE_SAVE_PATH + adminAnimalModifyBo.getRecordId() + ".png";
            String newAvatarFront = PICTURE_SAVE_PATH_FRONT + adminAnimalModifyBo.getRecordId() + ".png";
            try {
                Files.move(Paths.get(adminAnimalModifyBo.getAvatar()), Paths.get(newAvatar), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            animal.setAvatar(newAvatarFront);
        }
        animalMapper.updateByPrimaryKeySelective(animal);
    }

    public void adminAnimalDelete(AdminAnimalDeleteBo adminAnimalDeleteBo) {
        Animal animal = animalMapper.selectByPrimaryKey(adminAnimalDeleteBo.getRecordId());
        if (Objects.isNull(animal)) {
            throw new RuntimeException("Animal ID Does Not Exist");
        }

        animalMapper.deleteByPrimaryKey(animal.getId());
    }

    public Map<String, Object> animalGet(AnimalGetBo animalGetBo) {
        AnimalExample example = new AnimalExample();

        example.createCriteria().andNameLike("%" + animalGetBo.getContext() + "%");

        List<Animal> animalList = animalMapper.selectByExample(example);

        animalList.sort(Comparator.comparing(Animal::getName));

        List<AnimalGetVo> voList = animalList
                .stream()
                .map(e -> {
                    AnimalGetVo vo = new AnimalGetVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = animalGetBo.getPage() * animalGetBo.getPageNum();
        if (start >= voList.size()) {
            map.put("animals", null);
        } else {
            int end = Math.min(start + animalGetBo.getPageNum(), voList.size());
            map.put("animals", voList.subList(start, end));
        }
        return map;
    }

    public void adminAnimalAdd(AdminAnimalAddBo adminAnimalAddBo) {
        Animal animal = new Animal();
        animal.setAdopted(adminAnimalAddBo.getAdopted());
        animal.setIntro(adminAnimalAddBo.getIntro());
        animal.setName(adminAnimalAddBo.getName());
        if (Objects.isNull(adminAnimalAddBo.getAvatar())) {
            animal.setAvatar(DEFAULT_IMAGE_PATH);
        } else {
            animal.setAvatar(adminAnimalAddBo.getAvatar());
        }
        animalMapper.insertSelective(animal);
    }

    public BaseResponse animalAIPredict(String imgPath) {
        // 调用python进行预测
        String serverPath = "/root/AnimalRecognitionAI/predictLabel.py";
        String localPath = "D:/Software_data/Pycharm_prj/AnimalRecognitionAI/predictLabel.py";
        String predictPyPath = runMode.equals(LOCAL) ? localPath : serverPath;
        String pythonPath = runMode.equals(LOCAL) ? "C:\\Users\\Tantor\\.conda\\envs\\pytorch38\\python.exe" : "/root/anaconda3/envs/pytorch38/bin/python";
        String[] cmd = {pythonPath, predictPyPath, "--img_path", imgPath, "--mode", runMode};

        String predictTxtPath = runMode.equals(LOCAL) ? "C:/Users/Tantor/Desktop/predictLabel.txt" : "/root/AnimalManagement/temp/predictLabel.txt";
        for (String str : cmd) {
            System.out.print(str + " ");
        }
        System.out.println();

        Process proc;
        try {
            proc = Runtime.getRuntime().exec(cmd);  // 执行py文件
            System.out.println(proc.waitFor());
            System.out.println("子程序运行完了");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 加载txt文件，有关文件中的信息，去看pycharm中写的
        Scanner scanner = null; // 不能在try-catch中定义Scanner，它对外界不可见。记得初始化为null
        try {  // 在语句块内部定义的变量，作用域在语句块内部，外部不可见。
            File file = new File(predictTxtPath);
            scanner = new Scanner(new FileInputStream(file));
        } catch (Exception e) {
            throw new RuntimeException("error, can't find predictTxtPath");
        }
        int label = scanner.nextInt();
        if (label == -1) {
            // -1表示空地址
            return BaseResponse.builder().code(1).message("No match animal").build();
        } else if (label == -2) {
            // -2表示图片不存在
            return BaseResponse.builder().code(2).message("receive an empty img path").build();
        } else if (label == -3) {
            // -3表示没有匹配的动物
            return BaseResponse.builder().code(3).message("no match animal").build();
        } else {
            String animalName = predictId2Name.get(label);
            System.out.println("lable is " + Integer.toString(label));
            System.out.println("name  is " + animalName);
            AnimalExample animalExample = new AnimalExample();
            animalExample.createCriteria().andNameEqualTo(animalName);
            List<Animal> animals = animalMapper.selectByExample(animalExample);

            // 数据库中没有找到这一只动物
            if (animals.size() == 0) {
                System.out.println("数据库中没有找到这只动物");
                return BaseResponse.builder().code(1).message("There is a bug in the back end's SQL").build();
            }

            // 找到了这一只动物，返回相关信息
            AnimalAIVo vo = new AnimalAIVo();
            vo.setAnimalName(animalName);
            vo.setAnimalId(animals.get(0).getId());
            return BaseResponse.builder().code(0).message("success").body(vo).build();
        }

    }

}
