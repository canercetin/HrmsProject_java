package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.emailValidation.EmailValidationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.concretes.Employer;

@Service
public class EmployerManager implements EmployerService{

	private EmployerDao employerDao;
	private UserDao userDao;
	private EmailValidationService emailValidationService;
	
	@Autowired
	public EmployerManager(EmployerDao employerDao, UserDao userDao, EmailValidationService emailValidationService) {
		super();
		this.employerDao = employerDao;
		this.userDao = userDao;
		this.emailValidationService = emailValidationService;
	}

	@Override
	public DataResult<List<Employer>> getAll() {
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(), "Employers Listed!");
	}

	@Override
	public Result add(Employer employer) {
		
		if( employer.getEmail().isEmpty() || employer.getPassword().isEmpty() || 
			employer.getCompanyName().isEmpty()	|| employer.getWebAddress().isEmpty() || 
			employer.getPhoneNumber().isEmpty() )
		{
			return new ErrorResult("You must enter all informations!");
		}
		
		if(!this.emailValidationService.isValid(employer.getEmail()))
		{
			return new ErrorResult("Email address is not valid!");
		}
		if(!this.emailValidationService.isVerificationMailClicked(employer.getEmail()))
		{
			return new ErrorResult("You must click the verification mail on time!");
		}
		if(this.userDao.findByEmailEquals(employer.getEmail()) != null)
		{
			return new ErrorResult("Email address is already in use!");
		}
		
		this.employerDao.save(employer);	
		return new SuccessResult("New Employer Added!");
	}

}
