package net.skhu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import net.skhu.dto.Student;
import net.skhu.mapper.StudentMapper;
import net.skhu.model.ProfessorEdit;

@Service
public class ProfessorService {
	@Autowired
	StudentMapper studentMapper;
	ModelMapper modelMapper = new ModelMapper();

	public ProfessorEdit findOne(int id) {
		Student studentDto = studentMapper.findOne(id);
		return toEditModel(studentDto);
	}

	public Student findByStudentNo(String studentNo) {
		return studentMapper.findByStudentNo(studentNo);
	}

	public List<Student> findAll() {
		return studentMapper.findAll();
	}

	public void insert(ProfessorEdit studentEdit, BindingResult bindingResult) throws Exception {
		if (hasErrors(studentEdit, bindingResult))
			throw new Exception("입력 데이터 오류");
		Student student = toDto(studentEdit);
		studentMapper.insert(student);
	}

	public void update(ProfessorEdit studentEdit, BindingResult bindingResult) throws Exception {
		if (hasErrors(studentEdit, bindingResult))
			throw new Exception("입력 데이터 오류");
		Student student = toDto(studentEdit);
		studentMapper.update(student);
	}

	public void delete(int id) {
		studentMapper.delete(id);
	}

	public Student toDto(ProfessorEdit studentEdit) {
		return modelMapper.map(studentEdit, Student.class);
	}

	public ProfessorEdit toEditModel(Student studentDto) {
		return modelMapper.map(studentDto, ProfessorEdit.class);
	}

	public boolean hasErrors(ProfessorEdit studentEdit, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return true;
		Student student2 = findByStudentNo(studentEdit.getStudentNo());
		if (student2 != null && student2.getId() != studentEdit.getId()) {
			bindingResult.rejectValue("studentNo", null, "학번이 중복됩니다.");
			return true;
		}
		return false;
	}
}