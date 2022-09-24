
## Workbus

>Workbus is a generic workflow bus to automatizate determinated process
>
> [JDK] 17  
> [State] Work In Progress
### Install & Execution

In progress....

### Samples



     var executor = Workflow 
	     .<String>builder() 
	     .step("1", new StepTest()) 
	     .callback((o) -> {}) 
	     .step("2", StepTest.class) 
	     .end();  
	     
     executor.execute(); 
     executor.executePreviousStep();


## License

MIT - It's a complete open source code, feel free to copy and reproduce or contribute this.