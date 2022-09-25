
## Workbus

>Workbus is a generic workflow bus to automatizate determinated process
>
> [JDK] 17  

### Example

#### Step for register Personal data

    public class RegistrationPersonalDataStep extends WorkflowStep<RegistrationDTO>{
        public RegistrationPersonalDataStep(RegistrationDTO dto){
            this.data = dto;
        }        

        ...
    
        @Override
        protected void execute() {
            System.out.println("Success on save personal data");
        }
    }

#### Step for register Address data

    public class RegistrationAddressStep extends WorkflowStep<RegistrationDTO>{
        public RegistrationAddressStep(RegistrationDTO dto){
            this.data = dto;
        }        

        ...
    
        @Override
        protected void execute() {
            System.out.println("Success on save address data");
        }
    }

#### Pre step validation

    public class RegistrationPreStep extends WorkflowStep<Boolean> {
        
        @Override
        protected void execute() {
            if(isNull(registrationDTO.getName()) || registrationDTO.getName().isEmpty()){
                this.data = Boolean.FALSE;
                this.resultMessage = "Name must be not empty";
                return;
            }
    
            if(isNull(registrationDTO.getAge()) || registrationDTO.getAge() < 18) {
                this.data = Boolean.FALSE;
                this.resultMessage = "Age must be growth to 18 years old";
                return;
            }
    
            this.data = Boolean.TRUE;
        }

        @Override
        protected Boolean hasSuccess() {
            return data;
        }
    }

#### Workflow Build

    ...
    RegistrationDTO dto = new RegistrationDTO();

    dto.setName("");
    dto.setAge(15);

    var executor = Workflow.<String>builder()
            .pre(new RegistrationPreStep(dto))
            .step(RegistrationStepsEnum.PERSONAL_DATA.toString(), new RegistrationPersonalDataStep(dto))
            .<RegistrationDTO>callback(o -> System.out.println(o.getName()))
            .step(RegistrationStepsEnum.ADDRESS.toString(), new RegistrationAddressStep(dto))
            .end();

1. Define validation pre-step
2. Register personal data
3. Adding a callback to print the name
4. Another step with same dto for register address
5. end() method generate a WorkflowExecutor

#### Test case

    try{
        executor.execute();
    } catch (PreStepFailedException e) {
        System.out.println(e.getMessage());
    }

    dto.setName("Name");

    try{
        executor.execute();
    } catch (PreStepFailedException e) {
        System.out.println(e.getMessage());
    }

    dto.setAge(19);

    executor.execute();

#### Log Execution

> Pre Step failed, message: Name must be not empty

> Pre Step failed, message: Age must be growth to 18 years old

> Success on save personal data

> Name

> Success on save address data

## License

MIT - It's a complete open source code, feel free to copy and reproduce or contribute this.