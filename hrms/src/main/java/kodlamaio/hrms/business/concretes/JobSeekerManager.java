package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobSeekerService;
import kodlamaio.hrms.core.emailValidation.EmailValidationService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobSeekerDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.concretes.JobSeeker;

@Service
public class JobSeekerManager implements JobSeekerService{
	
	private JobSeekerDao jobSeekerDao;
	private UserDao userDao;
	private EmailValidationService emailValidationService;
	
	@Autowired
	public JobSeekerManager(JobSeekerDao jobSeekerDao, UserDao userDao, 
							EmailValidationService emailValidationService) {
		super();
		this.jobSeekerDao = jobSeekerDao;
		this.userDao = userDao;
		this.emailValidationService = emailValidationService;
	}

	@Override
	public DataResult<List<JobSeeker>> getAll() {
		return new SuccessDataResult<List<JobSeeker>>(this.jobSeekerDao.findAll(),"JobSeekers Listed!");
	}

	@Override
	public Result add(JobSeeker jobSeeker) {

		if( jobSeeker.getEmail().isEmpty() || jobSeeker.getPassword().isEmpty() ||
			jobSeeker.getFirstName().isEmpty() || jobSeeker.getLastName().isEmpty() ||
			jobSeeker.getBirthYear()==0 || jobSeeker.getIdentityNumber().isEmpty()    )
		{
			return new ErrorResult("You must enter all informations!");
		}
		
		if(userDao.findByEmailEquals(jobSeeker.getEmail()) != null)
		{
			return new ErrorResult("Email address already in use!");
		}		
		if(jobSeekerDao.findByIdentityNumberEquals(jobSeeker.getIdentityNumber()) != null)
		{
			return new ErrorResult("Identity number already in use!");
		}
		
		if(!emailValidationService.isValid(jobSeeker.getEmail()))
		{
			return new ErrorResult("Email adderss is not valid!");			
		}	
		if(!emailValidationService.isVerificationMailClicked(jobSeeker.getEmail()))
		{
			return new ErrorResult("You must click the verification mail on time!");
		}
			this.jobSeekerDao.save(jobSeeker);
			return new SuccessResult("New JobSeeker Added!");
	}

}
