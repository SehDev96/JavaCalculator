# JavaCalculator

## Summary 

*Time taken: Approximately 2+ days*

This application is a calculator which takes string as an input. The code loops through the full equation (user input) to find basic equations within a pair of parentheses (if any) to be solved first. Basic equation in this context means equations with no parentheses, e.g 5+4, 6*2-1. It solves the basic equation within the bracket and replaces the basic equation with the answer in the full equation (user input). The loop runs until all the parentheses are replaced with the corresponding answer which leaves the full equation in a form the basic equation. And the basic equation is resolved. To simplify the explanation: 

Input: 10 - ( 2 + 3 * ( 7 - ( 5 - 1 ) ) )   

- 10 - ( 2 + 3 * ( 7 - 4.0 ) ) 
- 10 - ( 2 + 3 * 3.0 ) 
- 10 - 11.0 
- -1.00


--- 


### Unfinished / Room for improvement 

- The validity of equation inputted by user in the code is rudimentary. It checks only the number of parentheses and the operators. It could be improved by providing a regex check for acceptable inputs. 


