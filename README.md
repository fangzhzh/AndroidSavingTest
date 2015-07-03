
| method  	|  file number 	| speed  	| with LRU  	|   
|---	|---	|---	|---	|
|  sharePrefernce 	|  1 	|   fanst	|   no	|   	
|  database 	|  about two files 	|  slow 	|  no 	|   	
| DiskLruCache  	|  many files 	|  slow 	|   yes	|   	
| LRUSharePreferenceCache | 1  | fast | yes |

The reason I need LRU is because SharePreference will crash when entry number exceeds about 10,000.
